package com.smpn1.bergas.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.*;
import com.smpn1.bergas.DTO.BeritaDTO;
import com.smpn1.bergas.model.Berita;
import com.smpn1.bergas.repository.BeritaRepository;
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
public class BeritaService {

    @Autowired
    private BeritaRepository beritaDao;




    private long id;

    public BeritaService() {
    }

    private static final String BASE_URL = "https://s3.lynk2.co/api/s3";

//    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/upload-image-example-3790f.appspot.com/o/%s?alt=media";

    public Berita save(BeritaDTO berita) throws Exception {
        Berita newBerita = new Berita();
        newBerita.setAuthor(berita.getAuthor());
        newBerita.setJudulBerita(berita.getJudulBerita());
        newBerita.setIsiBerita(berita.getIsiBerita());
        newBerita.setCategoryBerita(berita.getCategory());

        return beritaDao.save(newBerita);
    }

    public Optional<Berita> findById(Long id) {
        return Optional.ofNullable(beritaDao.findById(id));
    }

    public Page<Berita> findAllWithPagination(Pageable pageable) {
        return beritaDao.findAllByOrderByUpdatedDateDesc(pageable);
    }

    @Transactional
    public void delete(Long id) {
        Berita berita = beritaDao.findById(id);

        if (berita != null) {
            berita.setCategoryBerita(null);

            beritaDao.delete(berita);
        } else {
            System.out.println("Entity with id " + id + " not found.");
        }
    }

    public Berita update(Long id, BeritaDTO beritaDTO) throws Exception {
        Berita berita = beritaDao.findById(id);
        berita.setJudulBerita(beritaDTO.getJudulBerita());
        berita.setIsiBerita(beritaDTO.getIsiBerita());
        berita.setAuthor(beritaDTO.getAuthor());
        berita.setCategoryBerita(beritaDTO.getCategory());
        return beritaDao.save(berita);
    }
    public Berita updateFoto(Long id, MultipartFile multipartFile) throws Exception {
        Berita berita = beritaDao.findById(id);
        String image = uploadFile(multipartFile);
        berita.setImage(image);
        return beritaDao.save(berita);
    }


    public List<Berita> beritaTerbaru(){
        return beritaDao.findFirst5ByOrderByUpdatedDateDesc();
    }

    public List<Berita> searchBerita(String judul) {
        return beritaDao.searchByJudulBerita(judul);
    }

    public Berita getBeritaById(Long id) throws Exception {
        Berita berita = beritaDao.findById(id);
        if (berita == null) throw new Exception("Berita not found!!!");
        return berita;
    }

    public List<Berita> arsip(String bulan){
        return beritaDao.find(bulan);
    }




    public Page<Berita> getByCategory(String categoryId, Pageable pageable) {
        return beritaDao.findByCategoryBerita_Id(categoryId, pageable);
    }
    public List<Berita> relatedPosts(Long idBerita) throws Exception {
        String berita = beritaDao.getByIdBerita(idBerita);
        return beritaDao.relatedPost(berita);
    }

    public List<Berita> terbaruByCategory(Long categoryId) {
        return beritaDao.terbaruByCategory(categoryId);
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
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", multipartFile.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(base_url, HttpMethod.POST, requestEntity, String.class);
        String fileUrl = extractFileUrlFromResponse(response.getBody());
        return fileUrl;
    }

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