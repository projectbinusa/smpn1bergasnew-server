package com.smpn1.bergas.service;

import com.smpn1.bergas.model.CategoryProgram;
import com.smpn1.bergas.model.FotoSarana;
import com.smpn1.bergas.model.Program;
import com.smpn1.bergas.repository.CategoryProgramRepository;
import com.smpn1.bergas.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryProgramService {
    @Autowired
    private CategoryProgramRepository categoryProgramRepository;

    @Autowired
    private ProgramRepository programRepository;

    public CategoryProgram add(CategoryProgram categoryProgram){
        return categoryProgramRepository.save(categoryProgram);
    }
    public CategoryProgram getById(Long id){
        return categoryProgramRepository.findById(id).orElse(null);
    }
    public Page<CategoryProgram> getAll(Pageable pageable){
        return categoryProgramRepository.findAll(pageable);
    }
    public List<CategoryProgram> getAllNoPage(){
        return categoryProgramRepository.findAll();
    }
    public Page<CategoryProgram> getAllTerbaru(Pageable pageable) {
        return categoryProgramRepository.getAll(pageable);
    }
    public CategoryProgram edit(CategoryProgram categoryProgram ,Long id){
        CategoryProgram update = categoryProgramRepository.findById(id).orElse(null);
        update.setCategory(categoryProgram.getCategory());
        return categoryProgramRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            // Cek apakah ada foto terkait dengan sarana yang akan dihapus
            if (!programRepository.findByIdCategory(id).isEmpty()) {
                // Hapus semua entri foto terkait dengan id sarana
                List<Program> programs = programRepository.findByIdCategory(id);
                for (Program program : programs){
                    programRepository.deleteById(program.getId());
                }
            }

            // Hapus entri sarana setelah semua foto terkait dihapus
            categoryProgramRepository.deleteById(id);

            // Return response berhasil
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;

        } catch (Exception e) {
            // Return response gagal jika ada error
            return Collections.singletonMap("Deleted", Boolean.TRUE);
        }
    }
}
