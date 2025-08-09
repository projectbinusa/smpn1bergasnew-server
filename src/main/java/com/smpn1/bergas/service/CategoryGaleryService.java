package com.smpn1.bergas.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smpn1.bergas.model.CategoryGalery;
import com.smpn1.bergas.model.Galeri;
import com.smpn1.bergas.repository.CategoryGaleryRepository;
import com.smpn1.bergas.repository.GaleriRepository;

@Service
public class CategoryGaleryService {
    @Autowired
    private CategoryGaleryRepository categoryGaleryRepository;

    @Autowired
    private GaleriRepository galeriRepository;

    public CategoryGalery add(CategoryGalery categoryGalery){
        return categoryGaleryRepository.save(categoryGalery);
    }
    public CategoryGalery getById(Long id){
        return categoryGaleryRepository.findById(id).orElse(null);
    }
    public Page<CategoryGalery> getAll(Pageable pageable){
        return categoryGaleryRepository.findAll(pageable);
    }
    public List<CategoryGalery> getAllNoPage(){
        return categoryGaleryRepository.findAll();
    }
    public Page<CategoryGalery> getAllTerbaru(Pageable pageable) {
        return categoryGaleryRepository.getAll(pageable);
    }
    public CategoryGalery edit(CategoryGalery categoryGalery ,Long id){
        CategoryGalery update = categoryGaleryRepository.findById(id).orElse(null);
        update.setCategory(categoryGalery.getCategory());
        return categoryGaleryRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            // Cek apakah ada foto terkait dengan sarana yang akan dihapus
            if (!galeriRepository.findByIdCategory(id).isEmpty()) {
                // Hapus semua entri foto terkait dengan id sarana
                List<Galeri> galeries = galeriRepository.findByIdCategory(id);
                for (Galeri galeri : galeries){
                    galeriRepository.deleteById(galeri.getId());
                }
            }

            // Hapus entri sarana setelah semua foto terkait dihapus
            categoryGaleryRepository.deleteById(id);

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
