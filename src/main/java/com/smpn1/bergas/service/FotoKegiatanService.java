package com.smpn1.bergas.service;

import com.smpn1.bergas.DTO.FotoKegiatanDTO;
import com.smpn1.bergas.model.FotoKegiatan;
import com.smpn1.bergas.repository.FotoKegiatanRepository;
import com.smpn1.bergas.repository.KegiatanRepository;
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
public class FotoKegiatanService {
    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/upload-image-example-a0910.appspot.com/o/%s?alt=media";
    @Autowired
    private FotoKegiatanRepository fotoKegiatanRepository;

    @Autowired
    private KegiatanRepository kegiatanRepository;

    public FotoKegiatan add(FotoKegiatanDTO fotoKegiatanDTO, MultipartFile multipartFile) throws Exception {
        FotoKegiatan fotoKegiatan = new FotoKegiatan();
        String image = imageConverter(multipartFile);
        fotoKegiatan.setFoto(image);
        fotoKegiatan.setKegiatan(kegiatanRepository.findById(fotoKegiatanDTO.getId_kegiatan()).orElse(null));
        return fotoKegiatanRepository.save(fotoKegiatan);
    }
    public FotoKegiatan getById(Long id){
        return fotoKegiatanRepository.findById(id).orElse(null);
    }
    public Page<FotoKegiatan> getAll(Pageable pageable){
        return fotoKegiatanRepository.findAll(pageable);
    }
    public Page<FotoKegiatan> getAllByKegiatan(Long id ,Pageable pageable){
        return fotoKegiatanRepository.findByKegiatanId(id,pageable);
    }
    public FotoKegiatan edit(FotoKegiatanDTO fotoKegiatan , MultipartFile multipartFile , Long id) throws Exception {
        FotoKegiatan update = fotoKegiatanRepository.findById(id).orElse(null);
        update.setKegiatan(kegiatanRepository.findById(fotoKegiatan.getId_kegiatan()).orElse(null));
        String image = imageConverter(multipartFile);
        update.setFoto(image);
        return fotoKegiatanRepository.save(update);
    }

    public Map<String, Boolean> delete(Long id) {
        try {
            fotoKegiatanRepository.deleteById(id);
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
        BlobId blobId = BlobId.of("upload-image-example-a0910.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("bawaslu-firebase.json");
        Credentials credentials = GoogleCredentials.fromStream(serviceAccount);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

}
