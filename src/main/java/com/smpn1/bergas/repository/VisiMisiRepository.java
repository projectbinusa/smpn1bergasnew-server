package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.VisiMisi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VisiMisiRepository extends JpaRepository<VisiMisi ,Long> {
    @Query(value = "SELECT * FROM visi_misi ORDER BY updated_date DESC" ,nativeQuery = true)
    Page<VisiMisi> getAll(Pageable pageable);

    Page<VisiMisi> findByUserIdOrderByUpdatedDateDesc(
            Long userId,
            Pageable pageable);

    Page<VisiMisi> findByUserIdOrderByCreatedDateDesc(
            Long userId,
            Pageable pageable);
}
