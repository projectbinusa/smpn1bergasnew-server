package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.Kegiatan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface KegiatanRepository extends JpaRepository<Kegiatan , Long> {
    @Query(value = "SELECT * FROM kegiatan ORDER BY updated_date DESC" ,nativeQuery = true)
    Page<Kegiatan> getAll(Pageable pageable);

    @Query(value = "SELECT * FROM kegiatan WHERE category = :category", nativeQuery = true)
    Page<Kegiatan> getByCategory(String category , Pageable pageable);

    @Query(value = "SELECT * FROM kegiatan WHERE tanggal = :tanggal", nativeQuery = true)
    Page<Kegiatan> getByTanggal(Date tanggal , Pageable pageable);
}
