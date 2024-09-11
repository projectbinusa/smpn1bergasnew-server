package com.smpn1.bergas.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smpn1.bergas.model.Guru;
import com.smpn1.bergas.model.TenagaKependidikan;
import com.smpn1.bergas.repository.TenagaKependidikanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class TenagaKependidikanService {
    private static final String BASE_URL = "https://s3.lynk2.co/api/s3";
    @Autowired
    private TenagaKependidikanRepository tenagaKependidikanRepository;

    public TenagaKependidikan add(TenagaKependidikan tenagaKependidikan ) throws Exception {
        return tenagaKependidikanRepository.save(tenagaKependidikan);
    }
    public TenagaKependidikan getById(Long id){
        return tenagaKependidikanRepository.findById(id).orElse(null);
    }
    public Page<TenagaKependidikan> getAll(Pageable pageable){
        return tenagaKependidikanRepository.findAll(pageable);
    }
    public Page<TenagaKependidikan> getAllTerbaru(Pageable pageable) {
        return tenagaKependidikanRepository.getAll(pageable);
    }
    public TenagaKependidikan edit(TenagaKependidikan tenagaKependidikan, Long id) throws Exception {
        TenagaKependidikan update = tenagaKependidikanRepository.findById(id).orElse(null);
        update.setStatus(tenagaKependidikan.getStatus());
        update.setNama(tenagaKependidikan.getNama());
        update.setJabatan(tenagaKependidikan.getJabatan());
        return tenagaKependidikanRepository.save(update);
    }
    public TenagaKependidikan editFoto( MultipartFile multipartFile , Long id) throws Exception {
        TenagaKependidikan update = tenagaKependidikanRepository.findById(id).orElse(null);
        String image = uploadFile(multipartFile);
        update.setFoto(image);
        return tenagaKependidikanRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            tenagaKependidikanRepository.deleteById(id);
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
}
