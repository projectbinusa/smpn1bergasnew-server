package com.smpn1.bergas.service;

import com.smpn1.bergas.DTO.ProgramDTO;
import com.smpn1.bergas.model.Alumni;
import com.smpn1.bergas.model.Program;
import com.smpn1.bergas.repository.CategoryProgramRepository;
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

    @Autowired
    private CategoryProgramRepository categoryProgramRepository;

    public Program add(ProgramDTO programDTO){
        Program program = new Program();
        program.setCategoryProgram(categoryProgramRepository.findById(programDTO.getId_category()).orElse( null));
//        program.setJudulProgram(programDTO.getJudul());
        program.setNamaProgram(programDTO.getNama());
        program.setTujuan(programDTO.getTujuan());
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
    public Page<Program> getAllByKategory(Long id ,Pageable pageable) {
        return programRepository.findByIdCategory(id ,pageable);
    }
    public Page<Program> getByJudul(String judul , Pageable pageable){
        return programRepository.getByJudul(judul, pageable);
    }
    public Program edit(ProgramDTO program ,Long id){
        Program update = programRepository.findById(id).orElse(null);
//        update.setJudulProgram(program.getJudul());
        update.setNamaProgram(program.getNama());
        update.setTujuan(program.getTujuan());
        update.setCategoryProgram(categoryProgramRepository.findById(program.getId_category()).orElse( null));
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
