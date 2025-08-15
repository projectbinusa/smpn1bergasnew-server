package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.Prestasi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrestasiRepository extends JpaRepository<Prestasi , Long> {
    @Query(
            value = "SELECT * FROM prestasi ORDER BY updated_date DESC",
            countQuery = "SELECT COUNT(*) FROM prestasi",
            nativeQuery = true
    )
    Page<Prestasi> getAll(Pageable pageable);

    @Query(value = "SELECT * FROM prestasi p " +
            "WHERE LOWER(p.judul) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(p.nama_peserta) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "ORDER BY updated_date DESC",
            countQuery = "SELECT COUNT(*) FROM prestasi p " +
                    "WHERE LOWER(p.judul) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "   OR LOWER(p.nama_peserta) LIKE LOWER(CONCAT('%', :keyword, '%'))",
            nativeQuery = true)
    Page<Prestasi> searchAll(@Param("keyword") String keyword, Pageable pageable);
}
