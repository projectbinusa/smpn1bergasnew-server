package com.smpn1.bergas.service;

import com.smpn1.bergas.model.Alumni;
import com.smpn1.bergas.model.Program;
import com.smpn1.bergas.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProgramService {
    @Autowired
    private ProgramRepository programRepository;

    public Program add(Program program){
        return programRepository.save(program);
    }
    public Program getById(Long id){
        return programRepository.findById(id).orElse(null);
    }
    public Page<Program> getAll(Pageable pageable){
        return programRepository.findAll(pageable);
    }
    public Page<Program> getAllTerbaru(Pageable pageable) {
        return programRepository.getAll(pageable);
    }
    public Program edit(Program program ,Long id){
        Program update = programRepository.findById(id).orElse(null);
        update.setJudulProgram(program.getJudulProgram());
        update.setNamaProgram(program.getNamaProgram());
        update.setTujuan(program.getTujuan());
        return programRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            programRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
    }
}
