package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.Guru;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuruRepository extends JpaRepository<Guru, Long> {
    Page<Guru> findAll(Pageable pageable);
}
