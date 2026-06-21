package com.smpn1.bergas.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smpn1.bergas.model.LaporanBosp;

public interface LaporanBospRepository extends JpaRepository<LaporanBosp, Long> {

    LaporanBosp findById(long id);

    @Query(value = "SELECT * FROM laporan_bosp ORDER BY updated_date DESC", nativeQuery = true)
    Page<LaporanBosp> getAll(Pageable pageable);

    Page<LaporanBosp> findAllByOrderByUpdatedDateDesc(Pageable pageable);

    // @Query("SELECT l FROM LaporanBosp l WHERE LOWER(l.nama) LIKE LOWER(CONCAT('%', :nama, '%'))")
    // List<LaporanBosp> searchByNama(@Param("nama") String nama);
    @Query("SELECT p FROM LaporanBosp p WHERE p.nama LIKE CONCAT('%',:nama, '%')")
    List<LaporanBosp> searchByNama(String nama);

    Page<LaporanBosp> findByUserIdOrderByUpdatedDateDesc(
            Long userId,
            Pageable pageable);
}
