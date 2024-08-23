package com.smpn1.bergas.service;

import com.smpn1.bergas.model.VisiMisi;
import com.smpn1.bergas.repository.VisiMisiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class VisiMisiService {
    @Autowired
    private VisiMisiRepository visiMisiRepository;

    public VisiMisi add(VisiMisi visiMisi){
        return visiMisiRepository.save(visiMisi);
    }
    public VisiMisi getById(Long id){
        return visiMisiRepository.findById(id).orElse(null);
    }
    public Page<VisiMisi> getAll(Pageable pageable){
        return visiMisiRepository.findAll(pageable);
    }
    public Page<VisiMisi> getAllTerbaru(Pageable pageable) {
        return visiMisiRepository.getAll(pageable);
    }
    public VisiMisi edit(VisiMisi visiMisi ,Long id){
        VisiMisi update = visiMisiRepository.findById(id).orElse(null);
        update.setMisi(visiMisi.getMisi());
        update.setVisi(visiMisi.getVisi());
        update.setTujuan(visiMisi.getTujuan());
        return visiMisiRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            visiMisiRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
    }
}
