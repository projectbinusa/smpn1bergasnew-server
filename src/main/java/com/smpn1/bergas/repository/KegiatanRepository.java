package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.Kegiatan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KegiatanRepository extends JpaRepository<Kegiatan , Long> {
    @Query(value = "SELECT * FROM kegiatan ORDER BY update_date DESC" ,nativeQuery = true)
    Page<Kegiatan> getAll(Pageable pageable);
}
