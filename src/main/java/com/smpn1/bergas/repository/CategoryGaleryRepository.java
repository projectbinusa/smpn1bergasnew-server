package com.smpn1.bergas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smpn1.bergas.model.CategoryGalery;

public interface CategoryGaleryRepository extends JpaRepository<CategoryGalery , Long> {
    @Query(value = "SELECT * FROM category_program ORDER BY updated_date DESC" ,nativeQuery = true)
    Page<CategoryGalery> getAll(Pageable pageable);
}
