package com.smpn1.bergas.service;

import com.smpn1.bergas.model.Alumni;
import com.smpn1.bergas.model.Sarana;
import com.smpn1.bergas.repository.SaranaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class SaranaService {
    @Autowired
    private SaranaRepository saranaRepository;

    public Sarana add(Sarana sarana){
        return saranaRepository.save(sarana);
    }
    public Sarana getById(Long id){
        return saranaRepository.findById(id).orElse(null);
    }
    public Page<Sarana> getAll(Pageable pageable){
        return saranaRepository.findAll(pageable);
    }
    public Page<Sarana> getAllTerbaru(Pageable pageable) {
        return saranaRepository.getAll(pageable);
    }
    public Page<Sarana> getAllCategory(String category ,Pageable pageable) {
        return saranaRepository.getAllByCategory(category,pageable);
    }
    public Sarana edit(Sarana sarana ,Long id){
        Sarana update = saranaRepository.findById(id).orElse(null);
        update.setNama_sarana(sarana.getNama_sarana());
        update.setDeskripsi(sarana.getDeskripsi());
        update.setCategory(sarana.getCategory());
        return saranaRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            saranaRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
    }
}
