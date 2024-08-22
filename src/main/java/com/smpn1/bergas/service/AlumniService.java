package com.smpn1.bergas.service;

import com.smpn1.bergas.model.Alumni;
import com.smpn1.bergas.repository.AlumniRepository;
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
public class AlumniService {
    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/upload-image-example-3790f.appspot.com/o/%s?alt=media";
    @Autowired
    AlumniRepository alumniRepository;

    public Alumni add(Alumni alumni, MultipartFile multipartFile) throws Exception {
        String image = imageConverter(multipartFile);
        alumni.setFoto(image);
        return alumniRepository.save(alumni);
    }

    public Alumni getById(Long id) {
        return alumniRepository.findById(id).orElse(null);
    }

    public Page<Alumni> getAll(Pageable pageable) {
        return alumniRepository.findAll(pageable);
    }
    public Page<Alumni> getAllTerbaru(Pageable pageable) {
        return alumniRepository.getAll(pageable);
    }

    public Alumni edit(Alumni alumni, MultipartFile multipartFile, Long id) throws Exception {
        Alumni update = alumniRepository.findById(id).orElse(null);
        if (update != null) {
            String image = imageConverter(multipartFile);
            update.setBiografi(alumni.getBiografi());
            update.setNama(alumni.getNama());
            update.setFoto(image);
            return alumniRepository.save(update);
        }
        return null;
    }

    public Map<String, Boolean> delete(Long id) {
        try {
            alumniRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
    }

    private String imageConverter(MultipartFile multipartFile) throws Exception {
        try {
            String fileName = getFileNameWithExtension(multipartFile.getOriginalFilename());
            File file = convertFile(multipartFile, fileName);
            String responseUrl = uploadFile(file, fileName);
            file.delete();
            return responseUrl;
        } catch (Exception e) {
            e.printStackTrace(); // Memperbaiki pencetakan stack trace
            throw new Exception("Error upload file: " + e.getMessage());
        }
    }

    private String getFileNameWithExtension(String fileName) {
        return fileName != null && fileName.contains(".") ? fileName : null;
    }

    private File convertFile(MultipartFile multipartFile, String fileName) throws IOException {
        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }
        return file;
    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("upload-image-example-3790f.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build(); // Sesuaikan content type

        // Pastikan file `bawaslu-firebase.json` ada di classpath
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("bawaslu-firebase.json");
        if (serviceAccount == null) {
            throw new IOException("Service account file not found");
        }

        Credentials credentials = GoogleCredentials.fromStream(serviceAccount);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }
}
