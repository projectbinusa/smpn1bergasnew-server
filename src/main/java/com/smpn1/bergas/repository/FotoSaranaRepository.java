package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.FotoSarana;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FotoSaranaRepository extends JpaRepository<FotoSarana , Long> {
    @Query(value = "SELECT * FROM foto_sarana WHERE sarana_id = :id" , nativeQuery = true)
    Page<FotoSarana> findBySaranaId(Long id , Pageable pageable);

    @Query(value = "SELECT * FROM foto_sarana ORDER BY updated_date DESC" ,nativeQuery = true)
    Page<FotoSarana> getAll(Pageable pageable);
}
