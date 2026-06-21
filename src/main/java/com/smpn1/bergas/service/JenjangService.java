package com.smpn1.bergas.service;

import com.smpn1.bergas.model.Alumni;
import com.smpn1.bergas.model.Jenjang;
import com.smpn1.bergas.repository.JenjangRepository;
import com.smpn1.bergas.util.SecurityUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class JenjangService {
    @Autowired
    private JenjangRepository jenjangRepositoryRepository;

    @Autowired
    private SecurityUtil securityUtil;

    public Jenjang add(Jenjang jenjang) {
        if (jenjang.getNama_jenjang() != null && !jenjang.getNama_jenjang().isEmpty()) {
            String baseLink = jenjang.getNama_jenjang().trim().replaceAll("\\s+", "-").toUpperCase();
            String randomSuffix = String.format("%04d", new Random().nextInt(10000));
            jenjang.setLink(baseLink + "-" + randomSuffix);
        }
        jenjang.setUserId(securityUtil.getCurrentUserId());
        jenjang.setUserName(securityUtil.getCurrentUsername());
        return jenjangRepositoryRepository.save(jenjang);
    }
    public Jenjang getById(Long id){
        return jenjangRepositoryRepository.findById(id).orElse(null);
    }
    public Page<Jenjang> getAll(Pageable pageable){
        return jenjangRepositoryRepository.findAll(pageable);
    }
    public Page<Jenjang> findAllWithPaginationByUserId(
            Long userId,
            Pageable pageable) {
        return jenjangRepositoryRepository.findByUserIdOrderByUpdatedDateDesc(
                userId,
                pageable);
    }
    public Page<Jenjang> getAllTerbaru(Pageable pageable) {
        return jenjangRepositoryRepository.getAll(pageable);
    }
    public Jenjang edit(Jenjang jenjangRepository, Long id) {
        Jenjang update = jenjangRepositoryRepository.findById(id).orElse(null);
        if (update != null) {
            update.setNama_jenjang(jenjangRepository.getNama_jenjang());
            update.setDescription(jenjangRepository.getDescription());
            update.setUserId(securityUtil.getCurrentUserId());
            update.setUserName(securityUtil.getCurrentUsername());
            if (jenjangRepository.getNama_jenjang() != null && !jenjangRepository.getNama_jenjang().isEmpty()) {
                String baseLink = jenjangRepository.getNama_jenjang().trim().replaceAll("\\s+", "-").toUpperCase();
                String randomSuffix = String.format("%04d", new Random().nextInt(10000));
                update.setLink(baseLink + "-" + randomSuffix);
            }

            return jenjangRepositoryRepository.save(update);
        }
        return null;
    }

    public Map<String, Boolean> delete(Long id) {
        try {
            jenjangRepositoryRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
    }

    public Jenjang getByLink(String link) {
        return jenjangRepositoryRepository.findByLink(link);
    }
}
