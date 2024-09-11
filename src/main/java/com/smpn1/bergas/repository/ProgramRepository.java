package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.FotoKegiatan;
import com.smpn1.bergas.model.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program , Long> {
    @Query(value = "SELECT * FROM program ORDER BY updated_date DESC" ,nativeQuery = true)
    Page<Program> getAll(Pageable pageable);

    @Query(value = "SELECT * FROM program WHERE judul_program = :judul", nativeQuery = true)
    Page<Program> getByJudul(String judul , Pageable pageable);

    @Query(value = "SELECT * FROM program WHERE category_id = :id" , nativeQuery = true)
    List<Program> findByIdCategory(Long id);

    @Query(value = "SELECT * FROM program WHERE category_id = :id" , nativeQuery = true)
    Page<Program> findByIdCategory(Long id , Pageable pageable);
}
