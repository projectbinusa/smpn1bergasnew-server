package com.smpn1.bergas.service;

import com.smpn1.bergas.model.Alumni;
import com.smpn1.bergas.model.Kontak;
import com.smpn1.bergas.repository.KontakRespository;
import com.smpn1.bergas.util.SecurityUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class KontakService {
    @Autowired
    private KontakRespository kontakRespository;

    @Autowired
    private SecurityUtil securityUtil;

    public Kontak add(Kontak kontak){
        kontak.setUserId(securityUtil.getCurrentUserId());
        kontak.setUserName(securityUtil.getCurrentUsername());
        return kontakRespository.save(kontak);
    }
    public Kontak getById(Long id){
        return kontakRespository.findById(id).orElse(null);
    }
    public Page<Kontak> getAll(Pageable pageable){
        return kontakRespository.findAll(pageable);
    }
    public Page<Kontak> findAllWithPaginationByUserId(
            Long userId,
            Pageable pageable) {
        return kontakRespository.findByUserIdOrderByUpdatedDateDesc(
                userId,
                pageable);
    }
    public Page<Kontak> getAllTerbaru(Long userId, Pageable pageable) {
        return kontakRespository.findByUserIdOrderByUpdatedDateDesc(userId, pageable);
    }
    public Kontak edit(Kontak kontak , Long id){
        Kontak update = kontakRespository.findById(id).orElse(null);
        update.setAddress(kontak.getAddress());
        update.setEmail(kontak.getEmail());
        update.setFax(kontak.getFax());
        update.setPhone(kontak.getPhone());
        update.setUserId(securityUtil.getCurrentUserId());
        update.setUserName(securityUtil.getCurrentUsername());
        return kontakRespository.save(update);
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            kontakRespository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
    }

}
