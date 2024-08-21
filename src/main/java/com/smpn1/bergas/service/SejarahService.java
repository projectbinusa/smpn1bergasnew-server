package com.smpn1.bergas.service;

import com.smpn1.bergas.model.Alumni;
import com.smpn1.bergas.model.Sejarah;
import com.smpn1.bergas.repository.SejarahRepository;
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

    public Sejarah add(Sejarah sejarah){
        return sejarahRepository.save(sejarah);
    }
    public Sejarah getById(Long id){
        return sejarahRepository.findById(id).orElse(null);
    }
    public Page<Sejarah> getAll(Pageable pageable){
        return sejarahRepository.findAll(pageable);
    }
    public Page<Sejarah> getAllTerbaru(Pageable pageable) {
        return sejarahRepository.getAll(pageable);
    }
    public Sejarah edit(Sejarah sejarah ,Long id){
        Sejarah update = sejarahRepository.findById(id).orElse(null);
        update.setJudul(sejarah.getJudul());
        update.setIsi(sejarah.getIsi());
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
