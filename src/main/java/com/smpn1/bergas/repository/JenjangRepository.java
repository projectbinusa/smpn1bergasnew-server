package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.Jenjang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JenjangRepository extends JpaRepository<Jenjang, Long> {
    @Query(value = "SELECT * FROM jenjang ORDER BY updated_date DESC" ,nativeQuery = true)
    Page<Jenjang> getAll(Pageable pageable);
}