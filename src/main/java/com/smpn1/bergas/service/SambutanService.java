package com.smpn1.bergas.service;

import com.smpn1.bergas.model.Alumni;
import com.smpn1.bergas.model.Sambutan;
import com.smpn1.bergas.repository.SambutanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class SambutanService {
    @Autowired
    private SambutanRepository sambutanRepository;

    public Sambutan add(Sambutan sambutan){
        return sambutanRepository.save(sambutan);
    }
    public Sambutan getById(Long id){
        return sambutanRepository.findById(id).orElse(null);
    }
    public Page<Sambutan> getAll(Pageable pageable){
        return sambutanRepository.findAll(pageable);
    }
    public Page<Sambutan> getAllTerbaru(Pageable pageable) {
        return sambutanRepository.getAll(pageable);
    }
    public Sambutan edit(Sambutan sambutan , Long id){
        Sambutan update = sambutanRepository.findById(id).orElse(null);
        update.setNama(sambutan.getNama());
        update.setIsi(sambutan.getIsi());
        update.setNip(sambutan.getNip());
        return sambutanRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            sambutanRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
    }
}
