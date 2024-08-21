package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.FotoKegiatan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FotoKegiatanRepository extends JpaRepository<FotoKegiatan , Long> {
    @Query(value = "SELECT * FROM foto_kegiatan WHERE kegiatan_id = :id" , nativeQuery = true)
    Page<FotoKegiatan> findByKegiatanId(Long id , Pageable pageable);

    @Query(value = "SELECT * FROM foto_kegiatan ORDER BY update_date DESC" ,nativeQuery = true)
    Page<FotoKegiatan> getAll(Pageable pageable);
}
