package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.KotakSaran;
import com.smpn1.bergas.model.Perpustakaan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PerpustakaanRepository extends JpaRepository<Perpustakaan ,Long> {
    @Query(value = "SELECT * FROM perpustakaan ORDER BY updated_date DESC" ,nativeQuery = true)
    Page<Perpustakaan> getAll(Pageable pageable);
}
