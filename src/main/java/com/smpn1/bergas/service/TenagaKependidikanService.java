package com.smpn1.bergas.service;

import com.smpn1.bergas.model.TenagaKependidikan;
import com.smpn1.bergas.repository.TenagaKependidikanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class TenagaKependidikanService {
    @Autowired
    private TenagaKependidikanRepository tenagaKependidikanRepository;

    public TenagaKependidikan add(TenagaKependidikan tenagaKependidikan){
        return tenagaKependidikanRepository.save(tenagaKependidikan);
    }
    public TenagaKependidikan getById(Long id){
        return tenagaKependidikanRepository.findById(id).orElse(null);
    }
    public Page<TenagaKependidikan> getAll(Pageable pageable){
        return tenagaKependidikanRepository.findAll(pageable);
    }
    public TenagaKependidikan edit(TenagaKependidikan tenagaKependidikan ,Long id){
        TenagaKependidikan update = tenagaKependidikanRepository.findById(id).orElse(null);
        update.setStatus(tenagaKependidikan.getStatus());
        update.setNama(tenagaKependidikan.getNama());
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
}
