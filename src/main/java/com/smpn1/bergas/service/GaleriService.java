package com.smpn1.bergas.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smpn1.bergas.DTO.GaleriDTO;
import com.smpn1.bergas.model.Galeri;
import com.smpn1.bergas.repository.CategoryGaleryRepository;
import com.smpn1.bergas.repository.GaleriRepository;

@Service
public class GaleriService {

    @Autowired
    private CategoryGaleryRepository categoryGaleryRepository;

    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/upload-image-example-3790f.appspot.com/o/%s?alt=media";
    @Autowired
    private GaleriRepository galeriRepository;

    public Galeri add(GaleriDTO galeriDTO, MultipartFile[] files) throws Exception {
        List<String> uploadedUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String url = uploadFile(file);
            uploadedUrls.add(url);
        }

        Galeri galeri = new Galeri();

        ObjectMapper mapper = new ObjectMapper();
        galeri.setFoto(mapper.writeValueAsString(uploadedUrls)); // simpan sebagai JSON string
        // galeri.setCategoryGalery(galeriRepository.findById(galeriDTO.getId_category()).orElse(null));
        galeri.setCategoryGalery(categoryGaleryRepository.findById(galeriDTO.getId_category()).orElse(null));
        galeri.setJudul(galeriDTO.getJudul());
        galeri.setDeskripsi(galeriDTO.getDeskripsi());
        return galeriRepository.save(galeri);
    }
    // public Galeri add(Galeri galeri, MultipartFile[] files) throws Exception {
    //     List<String> uploadedUrls = new ArrayList<>();
    //     for (MultipartFile file : files) {
    //         String url = uploadFile(file);
    //         uploadedUrls.add(url);
    //     }

    //     ObjectMapper mapper = new ObjectMapper();
    //     galeri.setFoto(mapper.writeValueAsString(uploadedUrls)); // simpan sebagai JSON string
    //     Long categoryId = galeri.getCategoryGalery() != null ? galeri.getCategoryGalery().getId() : null;
    //     if (categoryId == null) {
    //         throw new IllegalArgumentException("Kategori galeri tidak boleh kosong.");
    //     }
    //     galeri.setCategoryGalery(
    //         categoryGaleryRepository.findById(categoryId)
    //             .orElseThrow(() -> new IllegalArgumentException("Kategori galeri tidak ditemukan."))
    //     );
    //     return galeriRepository.save(galeri);
    // }
    public Galeri getById(Long id) {
        return galeriRepository.findById(id).orElse(null);
    }

    public Page<Galeri> getAll(Pageable pageable) {
        return galeriRepository.findAll(pageable);
    }

    public Page<Galeri> getAllTerbaru(Pageable pageable) {
        return galeriRepository.getAll(pageable);
    }

    public Galeri edit(GaleriDTO galeriDTO, Long id, MultipartFile[] files) throws Exception {
        Galeri update = galeriRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Galeri tidak ditemukan"));

        ObjectMapper mapper = new ObjectMapper();

        // Ambil foto lama yang masih ingin disimpan (dikirim dari DTO)
        List<String> existingPhotos = new ArrayList<>();
        if (galeriDTO.getFoto() != null && !galeriDTO.getFoto().isEmpty()) {
            existingPhotos = mapper.readValue(galeriDTO.getFoto(), new TypeReference<List<String>>() {
            });
        }

        // Upload foto baru (jika ada)
        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String url = uploadFile(file);
                    existingPhotos.add(url);
                }
            }
        }

        // Simpan kembali foto gabungan
        update.setFoto(mapper.writeValueAsString(existingPhotos));

        // Update field lainnya
        update.setJudul(galeriDTO.getJudul());
        update.setDeskripsi(galeriDTO.getDeskripsi());
        update.setCategoryGalery(
                categoryGaleryRepository.findById(galeriDTO.getId_category())
                        .orElseThrow(() -> new IllegalArgumentException("Kategori tidak ditemukan"))
        );

        return galeriRepository.save(update);
    }
    // public Galeri edit(GaleriDTO galeriDTO, Long id) throws Exception {
    //     Galeri update = galeriRepository.findById(id).orElse(null);
    //     update.setJudul(galeriDTO.getJudul());
    //     update.setDeskripsi(galeriDTO.getDeskripsi());
    //     update.setCategoryGalery(categoryGaleryRepository.findById(galeriDTO.getId_category()).orElse( null));
    //     return galeriRepository.save(update);
    // }

    // public Galeri edit(Galeri galeri, Long id) throws Exception {
    //     Galeri update = galeriRepository.findById(id).orElse(null);
    //     update.setJudul(galeri.getJudul());
    //     update.setDeskripsi(galeri.getDeskripsi());
    //     Long categoryId = galeri.getCategoryGalery() != null ? galeri.getCategoryGalery().getId() : null;
    //     if (categoryId == null) {
    //         throw new IllegalArgumentException("Kategori galeri tidak boleh kosong.");
    //     }
    //     update.setCategoryGalery(
    //             categoryGaleryRepository.findById(categoryId)
    //                     .orElseThrow(() -> new IllegalArgumentException("Kategori galeri tidak ditemukan."))
    //     );
    //     return galeriRepository.save(update);
    // }
    public Galeri editFoto(MultipartFile[] files, Long id) throws Exception {
        Galeri update = galeriRepository.findById(id).orElse(null);
        if (update == null) {
            throw new Exception("Data tidak ditemukan");
        }

        List<String> uploadedUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String url = uploadFile(file);
            uploadedUrls.add(url);
        }

        ObjectMapper mapper = new ObjectMapper();
        update.setFoto(mapper.writeValueAsString(uploadedUrls));

        return galeriRepository.save(update);
    }

    public Map<String, Boolean> delete(Long id) {
        try {
            galeriRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
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
