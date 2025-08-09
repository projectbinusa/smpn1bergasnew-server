package com.smpn1.bergas.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.*;
import com.smpn1.bergas.DTO.LaporanBospDTO;
import com.smpn1.bergas.model.LaporanBosp;
import com.smpn1.bergas.repository.LaporanBospRepository;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class LaporanBospService {

    @Autowired
    private LaporanBospRepository laporanRepo;

    private long id;

    public LaporanBospService() {
    }

    private static final String BASE_URL = "https://s3.lynk2.co/api/s3";

    //    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/upload-image-example-3790f.appspot.com/o/%s?alt=media";
    public LaporanBosp save(LaporanBospDTO dto, MultipartFile[] multipartFiles) throws Exception {
        LaporanBosp laporan = new LaporanBosp();
        laporan.setNama(dto.getNama());
        laporan.setDeskripsi(dto.getDeskripsi());

        if (multipartFiles != null && multipartFiles.length > 0) {
            String[] fileUrls = new String[multipartFiles.length];
            for (int i = 0; i < multipartFiles.length; i++) {
                fileUrls[i] = uploadFile(multipartFiles[i]); // Upload ke S3
            }
            laporan.setFiles(fileUrls);
        }

        return laporanRepo.save(laporan);
    }

    public Optional<LaporanBosp> findById(Long id) {
        return laporanRepo.findById(id); // Sudah Optional<LaporanBosp>
    }

    public Page<LaporanBosp> findAllWithPagination(Pageable pageable) {
        return laporanRepo.findAllByOrderByUpdatedDateDesc(pageable);
    }

    @Transactional
    public void delete(Long id) {
        Optional<LaporanBosp> laporanOpt = laporanRepo.findById(id);
        if (laporanOpt.isPresent()) {
            laporanRepo.delete(laporanOpt.get());
        } else {
            System.out.println("Entity with id " + id + " not found.");
        }
    }

    public LaporanBosp update(Long id, LaporanBospDTO dto, MultipartFile[] multipartFiles) throws Exception {
        Optional<LaporanBosp> laporanOpt = laporanRepo.findById(id);

        if (laporanOpt.isPresent()) {
            LaporanBosp laporan = laporanOpt.get();
            laporan.setNama(dto.getNama());
            laporan.setDeskripsi(dto.getDeskripsi());

            if (multipartFiles != null && multipartFiles.length > 0) {
                String[] fileUrls = new String[multipartFiles.length];
                for (int i = 0; i < multipartFiles.length; i++) {
                    fileUrls[i] = uploadFile(multipartFiles[i]);
                }
                laporan.setFiles(fileUrls);
            }

            return laporanRepo.save(laporan);
        } else {
            throw new Exception("LaporanBosp not found with id " + id);
        }
    }

    // public LaporanBosp updateFoto(Long id, MultipartFile multipartFile) throws Exception {
    //     LaporanBosp berita = laporanRepo.findById(id);
    //     String image = uploadFile(multipartFile);
    //     berita.setImage(image);
    //     return laporanRepo.save(berita);
    // }
    // public List<LaporanBosp> beritaTerbaru() {
    //     return laporanRepo.findFirst5ByOrderByUpdatedDateDesc();
    // }
    public List<LaporanBosp> searchLaporanBosp(String nama) {
        return laporanRepo.searchByNama(nama);
    }

    public LaporanBosp getLaporanBospById(Long id) throws Exception {
        Optional<LaporanBosp> laporanOpt = laporanRepo.findById(id);

        if (!laporanOpt.isPresent()) {
            throw new Exception("LaporanBosp with id " + id + " not found!");
        }

        return laporanOpt.get(); // Ambil isinya
    }

    // public List<LaporanBosp> arsip(String bulan) {
    //     return laporanRepo.find(bulan);
    // }
    // public Page<LaporanBosp> getByCategory(String categoryId, Pageable pageable) {
    //     return laporanRepo.findByCategoryLaporanBosp_Id(categoryId, pageable);
    // }
    // public List<LaporanBosp> relatedPosts(Long idLaporanBosp) throws Exception {
    //     String berita = laporanRepo.getByIdLaporanBosp(idLaporanBosp);
    //     return laporanRepo.relatedPost(berita);
    // }
    // public List<LaporanBosp> terbaruByCategory(Long categoryId) {
    //     return laporanRepo.terbaruByCategory(categoryId);
    // }
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
    // DIPAKAI
    // private String getExtension(String fileName) {
    //     return fileName.split("\\.")[0];
    // }
    // private File convertFile(MultipartFile multipartFile, String fileName) throws IOException {
    //     File file = new File(fileName);
    //     try (FileOutputStream fos = new FileOutputStream(file)) {
    //         fos.write(multipartFile.getBytes());
    //         fos.close();
    //     }
    //     System.out.println("File size: " + file.length());
    //     return file;
    // }
     private String extractFileUrlFromResponse(String responseBody) throws IOException {
         ObjectMapper mapper = new ObjectMapper();
         JsonNode jsonResponse = mapper.readTree(responseBody);
         JsonNode dataNode = jsonResponse.path("data");
         String urlFile = dataNode.path("url_file").asText();
         return urlFile;
     }
     private String uploadFile(MultipartFile multipartFile) throws IOException {
         RestTemplate restTemplate = new RestTemplate();
         // String base_url = "https://s3.lynk2.co/api/s3/slbc/images";
         String base_url = "https://s3.lynk2.co/api/s3/absenMasuk";
         org.springframework.http.HttpHeaders headers = new HttpHeaders();
         headers.setContentType(MediaType.MULTIPART_FORM_DATA);
         MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
         body.add("file", multipartFile.getResource());
         HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
         ResponseEntity<String> response = restTemplate.exchange(base_url, HttpMethod.POST, requestEntity, String.class);
         String fileUrl = extractFileUrlFromResponse(response.getBody());
         return fileUrl;
     }
    // SAMPAI SINI
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
