package com.smpn1.bergas.service;

import com.smpn1.bergas.model.Ekstrakurikuler;
import com.smpn1.bergas.repository.EkstrakurikulerRepository;
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
public class EkstrakurikulerService {
    @Autowired
    private EkstrakurikulerRepository ekstrakurikulerRepository;

    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/bawaslu-a6bd2.appspot.com/o/%s?alt=media";


    public Ekstrakurikuler add(Ekstrakurikuler ekstrakurikuler , MultipartFile multipartFile) throws Exception {
        String image = imageConverter(multipartFile);
        ekstrakurikuler.setFoto(image);
        return ekstrakurikulerRepository.save(ekstrakurikuler);
    }
    public Ekstrakurikuler getById(Long id){
        return ekstrakurikulerRepository.findById(id).orElse(null);
    }
    public Page<Ekstrakurikuler> getAll(Pageable pageable){
        return ekstrakurikulerRepository.findAll(pageable);
    }
    public Ekstrakurikuler edit(Ekstrakurikuler ekstrakurikuler , Long id , MultipartFile multipartFile) throws Exception {
        String image = imageConverter(multipartFile);
        Ekstrakurikuler update = ekstrakurikulerRepository.findById(id).orElse(null);
        update.setName(ekstrakurikuler.getName());
        update.setDeskripsi(ekstrakurikuler.getDeskripsi());
        update.setJadwal(ekstrakurikuler.getJadwal());
        update.setKoordinator(ekstrakurikuler.getKoordinator());
        update.setPembimbing(ekstrakurikuler.getPembimbing());
        update.setTempat(ekstrakurikuler.getTempat());
        update.setPrestasi(ekstrakurikuler.getPrestasi());
        update.setFoto(image);
        return ekstrakurikulerRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            ekstrakurikulerRepository.deleteById(id);
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
        BlobId blobId = BlobId.of("bawaslu-a6bd2.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("bawaslu-firebase.json");
        Credentials credentials = GoogleCredentials.fromStream(serviceAccount);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }
}
