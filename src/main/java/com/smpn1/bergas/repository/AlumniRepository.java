package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.Alumni;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlumniRepository extends JpaRepository<Alumni , Long> {
    @Query(value = "SELECT * FROM alumni ORDER BY updated_date DESC" ,nativeQuery = true)
    Page<Alumni> getAll(Pageable pageable);

}
