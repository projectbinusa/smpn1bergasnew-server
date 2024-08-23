package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.Sarana;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaranaRepository extends JpaRepository<Sarana , Long > {
    @Query(value = "SELECT * FROM sarana ORDER BY updated_date DESC" ,nativeQuery = true)
    Page<Sarana> getAll(Pageable pageable);
    @Query(value = "SELECT * FROM sarana WHERE category = :category" , nativeQuery = true)
    Page<Sarana> getAllByCategory(String category , Pageable pageable);
}
