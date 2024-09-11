package com.smpn1.bergas.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smpn1.bergas.model.Osis;
import com.smpn1.bergas.repository.OsisRepository;
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
public class OsisService {
    @Autowired
    private OsisRepository osisRepository;

    public Osis add(Osis osis) throws Exception {
        return osisRepository.save(osis);
    }

    public Osis getById(Long id) {
        return osisRepository.findById(id).orElse(null);
    }

    public Page<Osis> getAll(Pageable pageable) {
        return osisRepository.findAll(pageable);
    }
    public Page<Osis> getAllTerbaru(Pageable pageable) {
        return osisRepository.getAll(pageable);
    }

    public Osis edit(Osis osis, Long id) throws Exception {
        Osis update = osisRepository.findById(id).orElse(null);
        if (update != null) {
            update.setJabatan(osis.getJabatan());
            update.setNama(osis.getNama());
            update.setKelas(osis.getKelas());
            update.setTahunJabat(osis.getTahunJabat());
            update.setTahunTuntas(osis.getTahunTuntas());
            return osisRepository.save(update);
        }
        return null;
    }
    public Osis editFoto( MultipartFile multipartFile, Long id) throws Exception {
        Osis update = osisRepository.findById(id).orElse(null);
        if (update != null) {
            String image = uploadFIle(multipartFile);
            update.setFoto(image);
            return osisRepository.save(update);
        }
        return null;
    }

    public Map<String, Boolean> delete(Long id) {
        try {
            osisRepository.deleteById(id);
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
