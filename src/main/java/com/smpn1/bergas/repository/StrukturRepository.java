package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.Struktur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StrukturRepository extends JpaRepository<Struktur , Long> {
    @Query(value = "SELECT * FROM struktur WHERE jenis = :id AND ORDER BY update_date DESC" , nativeQuery = true)
    Page<Struktur> findByJenisId(String id , Pageable pageable);
    @Query(value = "SELECT * FROM struktur ORDER BY updated_date DESC" ,nativeQuery = true)
    Page<Struktur> getAll(Pageable pageable);

}
