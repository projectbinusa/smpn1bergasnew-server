package com.smpn1.bergas.service;

import com.smpn1.bergas.DTO.KotakSaranDTO;
import com.smpn1.bergas.model.KotakSaran;
import com.smpn1.bergas.repository.KotakSaranRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class KotakSaranService {
    @Autowired
    private KotakSaranRepository kotakSaranRepository;

    public KotakSaran add(KotakSaranDTO kotakSaran){
        KotakSaran kotakSaran1 = new KotakSaran();
        kotakSaran1.setTelp(kotakSaran.getTlp());
        kotakSaran1.setPesan(kotakSaran.getPesan());
        kotakSaran1.setNama(kotakSaran.getNama());
        kotakSaran1.setEmail(kotakSaran.getEmail());
        return kotakSaranRepository.save(kotakSaran1);
    }
    public KotakSaran getById(Long id){
        return kotakSaranRepository.findById(id).orElse(null);
    }
    public Page<KotakSaran> getAll(Pageable pageable){
        return kotakSaranRepository.findAll(pageable);
    }
    public Page<KotakSaran> getAllTerbaru(Pageable pageable) {
        return kotakSaranRepository.getAll(pageable);
    }
    public KotakSaran edit(KotakSaran kotakSaran , Long id){
        KotakSaran update = kotakSaranRepository.findById(id).orElse(null);
        update.setNama(kotakSaran.getNama());
        update.setEmail(kotakSaran.getEmail());
        update.setPesan(kotakSaran.getPesan());
        update.setTelp(kotakSaran.getTelp());
        return kotakSaranRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            kotakSaranRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
    }
}
