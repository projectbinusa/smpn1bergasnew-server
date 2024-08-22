package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.Sejarah;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SejarahRepository extends JpaRepository<Sejarah , Long> {
    @Query(value = "SELECT * FROM sejarah ORDER BY updated_date DESC" ,nativeQuery = true)
    Page<Sejarah> getAll(Pageable pageable);
}
