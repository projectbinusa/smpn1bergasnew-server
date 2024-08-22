package com.smpn1.bergas.service;

import com.smpn1.bergas.DTO.StrukturDTO;
import com.smpn1.bergas.model.Struktur;
import com.smpn1.bergas.repository.StrukturRepository;
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
public class StrukturService {
    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/upload-image-example-3790f.appspot.com/o/%s?alt=media";
    @Autowired
    private StrukturRepository strukturRepository;



    public Struktur add(StrukturDTO struktur, MultipartFile multipartFile) throws Exception {
        Struktur newStruktur = new Struktur();
        String image = imageConverter(multipartFile);
        newStruktur.setNama(struktur.getNama());
        newStruktur.setJabatan(struktur.getJabatan());
        newStruktur.setFoto(image);
        newStruktur.setTugas(struktur.getTugas());
        newStruktur.setJenisStruktur(struktur.getJenis());

        return strukturRepository.save(newStruktur);
    }

    public Struktur findById(Long id) {
        return strukturRepository.findById(id).orElse(null);
    }

    public Page<Struktur> getAll(Pageable pageable) {
        return strukturRepository.findAll(pageable);
    }

    public Page<Struktur> getAllTerbaru(Pageable pageable) {
        return strukturRepository.getAll(pageable);
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            strukturRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
    }

    public Struktur edit(Long id, StrukturDTO strukturDTO, MultipartFile multipartFile) throws Exception {
        Struktur struktur = strukturRepository.findById(id).orElse(null);
        String image = imageConverter(multipartFile);
        struktur.setNama(strukturDTO.getNama());
        struktur.setJabatan(strukturDTO.getJabatan());
        struktur.setFoto(image);
        struktur.setTugas(strukturDTO.getTugas());
        struktur.setJenisStruktur(strukturDTO.getJenis());

        return strukturRepository.save(struktur);
    }

    public Page<Struktur> getByJenis(String id, Pageable pageable) {
        return strukturRepository.findByJenisId(id, pageable);
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
