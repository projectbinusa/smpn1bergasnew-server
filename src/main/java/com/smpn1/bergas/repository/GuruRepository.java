package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.Guru;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GuruRepository extends JpaRepository<Guru, Long> {
    @Query(value = "SELECT * FROM guru ORDER BY updated_date DESC" ,nativeQuery = true)
    Page<Guru> getAll(Pageable pageable);

}
