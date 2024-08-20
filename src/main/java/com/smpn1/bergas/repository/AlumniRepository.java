package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.Alumni;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumniRepository extends JpaRepository<Alumni , Long> {
}
