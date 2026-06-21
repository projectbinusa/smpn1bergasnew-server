package com.smpn1.bergas.service;

import com.smpn1.bergas.model.Alumni;
import com.smpn1.bergas.model.Sejarah;
import com.smpn1.bergas.repository.SejarahRepository;
import com.smpn1.bergas.util.SecurityUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class SejarahService {
    @Autowired
    private SejarahRepository sejarahRepository;

    @Autowired
    private SecurityUtil securityUtil;

    public Sejarah add(Sejarah sejarah){
        sejarah.setUserId(securityUtil.getCurrentUserId());
        sejarah.setUserName(securityUtil.getCurrentUsername());
        return sejarahRepository.save(sejarah);
    }
    public Sejarah getById(Long id){
        return sejarahRepository.findById(id).orElse(null);
    }
    public Page<Sejarah> getAll(Pageable pageable){
        return sejarahRepository.findAll(pageable);
    }
    public Page<Sejarah> findAllWithPaginationByUserId(
            Long userId,
            Pageable pageable) {
        return sejarahRepository.findByUserIdOrderByUpdatedDateDesc(
                userId,
                pageable);
    }
    public Page<Sejarah> getAllTerbaru(Long userId, Pageable pageable) {
        return sejarahRepository.findByUserIdOrderByCreatedDateDesc(userId, pageable);
    }
    public Sejarah edit(Sejarah sejarah ,Long id){
        Sejarah update = sejarahRepository.findById(id).orElse(null);
        update.setJudul(sejarah.getJudul());
        update.setIsi(sejarah.getIsi());
        update.setUserId(securityUtil.getCurrentUserId());
        update.setUserName(securityUtil.getCurrentUsername());
        return sejarahRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            sejarahRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
    }
}
