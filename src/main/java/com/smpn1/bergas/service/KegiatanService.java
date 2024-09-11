package com.smpn1.bergas.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smpn1.bergas.model.Alumni;
import com.smpn1.bergas.model.FotoKegiatan;
import com.smpn1.bergas.model.Kegiatan;
import com.smpn1.bergas.repository.FotoKegiatanRepository;
import com.smpn1.bergas.model.FotoSarana;
import com.smpn1.bergas.model.Kegiatan;
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
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

@Service
public class KegiatanService {
    @Autowired
    private KegiatanRepository kegiatanRepository;

    @Autowired
    private FotoKegiatanRepository fotoKegiatanRepository;

    private static final String BASE_URL = "https://s3.lynk2.co/api/s3";

    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/upload-image-example-3790f.appspot.com/o/%s?alt=media";

    public Kegiatan add(Kegiatan kegiatan) throws Exception {
        return kegiatanRepository.save(kegiatan);
    }

    public Kegiatan findById(Long id) {
        return kegiatanRepository.findById(id).orElse(null);
    }

    public Page<Kegiatan> getAll(Pageable pageable) {
        return kegiatanRepository.findAll(pageable);
    }
    public Page<Kegiatan> getAllTerbaru(Pageable pageable) {
        return kegiatanRepository.getAll(pageable);
    }
    public Page<Kegiatan> getByCategory(String category , Pageable pageable){
        return kegiatanRepository.getByCategory(category,pageable);
    }
    public Page<Kegiatan> getByTanggal(Date tanggal , Pageable pageable){
        return kegiatanRepository.getByTanggal(tanggal,pageable);
    }

    public Map<String, Boolean> delete(Long id) {
        try {
            if (!fotoKegiatanRepository.findByIdKegiatan(id).isEmpty()){
                List<FotoKegiatan> fotoKegiatans = fotoKegiatanRepository.findByIdKegiatan(id);
                for (FotoKegiatan fotoKegiatan : fotoKegiatans){
                    fotoKegiatanRepository.deleteById(fotoKegiatan.getId());
                }
            }
            kegiatanRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;

        } catch (Exception e) {
            // Return response gagal jika ada error
            return Collections.singletonMap("Deleted", Boolean.TRUE);
        }
    }

//    public Map<String, Boolean> delete(Long id) {
//        try {
//            kegiatanRepository.deleteById(id);
//            Map<String, Boolean> response = new HashMap<>();
//            response.put("Deleted", Boolean.TRUE);
//            return response;
//        } catch (Exception e) {
//            return Collections.singletonMap("Deleted", Boolean.FALSE);
//        }
//    }

    public Kegiatan edit(Long id, Kegiatan kegiatan) throws Exception {
        Kegiatan update = kegiatanRepository.findById(id).orElse(null);
        update.setJudul(kegiatan.getJudul());
        update.setIsi(kegiatan.getIsi());
        update.setPenulis(kegiatan.getPenulis());
        update.setTanggal(kegiatan.getTanggal());
        return kegiatanRepository.save(update);
    }
    public Kegiatan editFoto(Long id, MultipartFile multipartFile) throws Exception {
        Kegiatan update = kegiatanRepository.findById(id).orElse(null);
        String image = uploadFile(multipartFile);
        update.setFoto(image);
        return kegiatanRepository.save(update);
    }

    private String extractFileUrlFromResponse(String responseBody) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(responseBody);
        JsonNode dataNode = jsonResponse.path("data");
        String urlFile = dataNode.path("url_file").asText();

        return urlFile;
    }

    private String uploadFile(MultipartFile multipartFile) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String base_url = "https://s3.lynk2.co/api/s3/absenMasuk";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", multipartFile.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(base_url, HttpMethod.POST, requestEntity, String.class);
        String fileUrl = extractFileUrlFromResponse(response.getBody());
        return fileUrl;
    }

//    private String imageConverter(MultipartFile multipartFile) throws Exception {
//        try {
//            String fileName = getExtension(multipartFile.getOriginalFilename());
//            File file = convertFile(multipartFile, fileName);
//            var RESPONSE_URL = uploadFile(file, fileName);
//            file.delete();
//            return RESPONSE_URL;
//        } catch (Exception e) {
//            e.getStackTrace();
//            throw new Exception("Error upload file: " + e.getMessage());
//        }
//    }
//
//    private String getExtension(String fileName) {
//        return  fileName.split("\\.")[0];
//    }
//
//    private File convertFile(MultipartFile multipartFile, String fileName) throws IOException {
//        File file = new File(fileName);
//        try (FileOutputStream fos = new FileOutputStream(file)) {
//            fos.write(multipartFile.getBytes());
//            fos.close();
//        }
//        System.out.println("File size: " + file.length());
//        return file;
//    }
//
//    private String uploadFile(File file, String fileName) throws IOException {
//        BlobId blobId = BlobId.of("upload-image-example-3790f.appspot.com", fileName);
//        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
//        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("bawaslu-firebase.json");
//        Credentials credentials = GoogleCredentials.fromStream(serviceAccount);
//        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
//        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
//        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
//    }
}
