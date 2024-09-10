package com.smpn1.bergas.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smpn1.bergas.model.MateriAjar;
import com.smpn1.bergas.repository.MateriAjarRepository;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class MateriAjarService {
    @Autowired
    private MateriAjarRepository materiAjarRepository;

    private static final String BASE_URL = "https://s3.lynk2.co/api/s3";

    public MateriAjar add(MateriAjar materiAjar, MultipartFile multipartFile) throws Exception {
        String file = uploadFIle(multipartFile);
        materiAjar.setIsi(file);
        return materiAjarRepository.save(materiAjar);
    }

    public MateriAjar getById(Long id) {
        return materiAjarRepository.findById(id).orElse(null);
    }

    public Page<MateriAjar> getAll(Pageable pageable) {
        return materiAjarRepository.findAll(pageable);
    }
    public Page<MateriAjar> getAllTerbaru(Pageable pageable) {
        return materiAjarRepository.getAll(pageable);
    }

    public MateriAjar edit(MateriAjar materiAjar, Long id) throws Exception {
        MateriAjar update = materiAjarRepository.findById(id).orElse(null);
        if (update != null) {
            update.setJenis(materiAjar.getJenis());
            update.setMapel(materiAjar.getMapel());
            update.setJudul(materiAjar.getJudul());
            update.setPenyusun(materiAjar.getPenyusun());
            update.setTingkat(materiAjar.getTingkat());
//            update.setTglUpload(materiAjar.getTglUpload());
            return materiAjarRepository.save(update);
        }
        return null;
    }
    public MateriAjar editFile( MultipartFile multipartFile, Long id) throws Exception {
        MateriAjar update = materiAjarRepository.findById(id).orElse(null);
        if (update != null) {
            String file = uploadFIle(multipartFile);
            update.setIsi(file);
            return materiAjarRepository.save(update);
        }
        return null;
    }

    public Map<String, Boolean> delete(Long id) {
        try {
            materiAjarRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
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
    private String extractFileUrlFromResponse(String responseBody) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(responseBody);
        JsonNode dataNode = jsonResponse.path("data");
        String urlFile = dataNode.path("url_file").asText();

        return urlFile;
    }

    private String uploadFIle(MultipartFile multipartFile) throws IOException {
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
}
