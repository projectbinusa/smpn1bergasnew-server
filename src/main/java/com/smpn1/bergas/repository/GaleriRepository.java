package com.smpn1.bergas.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smpn1.bergas.model.Berita;
import com.smpn1.bergas.model.Galeri;

public interface GaleriRepository extends JpaRepository<Galeri, Long> {

    @Query(value = "SELECT * FROM galeri ORDER BY updated_date DESC", nativeQuery = true)
    Page<Galeri> getAll(Pageable pageable);

    @Query(value = "SELECT * FROM galeri WHERE category_id = :id", nativeQuery = true)
    List<Galeri> findByIdCategory(Long id);

    @Query(value = "SELECT * FROM galeri WHERE category_id = :id", nativeQuery = true)
    Page<Galeri> findByIdCategory(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM galeri WHERE category_id = :categoryId AND user_id = :userId", nativeQuery = true)
    Page<Galeri> findByCategoryIdAndUserId(@Param("categoryId") Long categoryId, @Param("userId") Long userId, Pageable pageable);

    Page<Galeri> findByUserIdOrderByUpdatedDateDesc(
            Long userId,
            Pageable pageable);

}
