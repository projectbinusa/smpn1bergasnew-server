package com.smpn1.bergas.service;

import com.smpn1.bergas.model.Alumni;
import com.smpn1.bergas.model.Prestasi;
import com.smpn1.bergas.repository.PrestasiRepository;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class PrestasiService {
    @Autowired
    PrestasiRepository prestasiRepository;

    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/upload-image-example-3790f.appspot.com/o/%s?alt=media";


    public Prestasi add(Prestasi prestasi , MultipartFile multipartFile) throws Exception {
        String foto = imageConverter(multipartFile);
        prestasi.setFoto(foto);
        prestasi.setJudul(prestasi.getJudul());
        prestasi.setPeyelenggara(prestasi.getPeyelenggara());
        prestasi.setSkala(prestasi.getSkala());
        prestasi.setNama_peserta(prestasi.getNama_peserta());
        prestasi.setTanggal(prestasi.getTanggal());
        return prestasiRepository.save(prestasi);
    }
    public Prestasi edit(Prestasi prestasi , MultipartFile multipartFile , Long id) throws Exception {
        Prestasi update = prestasiRepository.findById(id).orElse(null);
        String foto = imageConverter(multipartFile);
        update.setFoto(foto);
        update.setJudul(prestasi.getJudul());
        update.setPeyelenggara(prestasi.getPeyelenggara());
        update.setSkala(prestasi.getSkala());
        update.setNama_peserta(prestasi.getNama_peserta());
        update.setTanggal(prestasi.getTanggal());
        return prestasiRepository.save(prestasi);
    }
    public Prestasi getByid(Long id){
        return prestasiRepository.findById(id).orElse(null);
    }
    public Page<Prestasi> getAll(Pageable pageable){
        return prestasiRepository.findAll(pageable);
    }
    public Page<Prestasi> getAllTerbaru(Pageable pageable) {
        return prestasiRepository.getAll(pageable);
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            prestasiRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
    }





    private String imageConverter(MultipartFile multipartFile) throws Exception {
        try {
            String fileName = getExtension(multipartFile.getOriginalFilename());
            File file = convertFile(multipartFile, fileName);
            var RESPONSE_URL = uploadFile(file, fileName);
            file.delete();
            return RESPONSE_URL;
        } catch (Exception e) {
            e.getStackTrace();
            throw new Exception("Error upload file: " + e.getMessage());
        }
    }

    private String getExtension(String fileName) {
        return  fileName.split("\\.")[0];
    }

    private File convertFile(MultipartFile multipartFile, String fileName) throws IOException {
        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        System.out.println("File size: " + file.length());
        return file;
    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("upload-image-example-3790f.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("bawaslu-firebase.json");
        Credentials credentials = GoogleCredentials.fromStream(serviceAccount);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }
}
