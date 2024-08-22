package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.Keuangan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeuanganRepository extends JpaRepository<Keuangan , Long > {
    @Query(value = "SELECT * FROM keuangan  WHERE category = :category ", nativeQuery = true)
    Page<Keuangan> findByCategoryKeuangan_Id(String category, Pageable pageable);

    @Query(value = "SELECT * FROM keuangan ORDER BY updated_date DESC" ,nativeQuery = true)
    Page<Keuangan> getAll(Pageable pageable);
}
