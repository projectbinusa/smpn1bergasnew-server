package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProgramRepository extends JpaRepository<Program , Long> {
    @Query(value = "SELECT * FROM program ORDER BY update_date DESC" ,nativeQuery = true)
    Page<Program> getAll(Pageable pageable);
}
