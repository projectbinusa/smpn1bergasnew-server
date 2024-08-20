package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.Berita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface BeritaRepository extends CrudRepository<Berita, Integer> {
    Berita findById(long id);
    Page<Berita> findAll(Pageable pageable);
    Page<Berita> findAllByOrderByUpdatedDateDesc(Pageable pageable);
    List<Berita> findFirst5ByOrderByUpdatedDateDesc();
    @Query(value = "SELECT * FROM berita  WHERE category = :category", nativeQuery = true)
    Page<Berita> findByCategoryBerita_Id(String category, Pageable pageable);

    @Query("SELECT p FROM Berita p WHERE " +
            "p.judulBerita LIKE CONCAT('%',:judul, '%')")
    List<Berita> searchByJudulBerita(String judul);

    @Query("SELECT p FROM Berita p WHERE DATE_FORMAT(p.createdDate, '%Y-%m') LIKE CONCAT('%', :bulan, '%')")
    List<Berita> find(String bulan);

    @Query(value = "SELECT * FROM berita t1 INNER JOIN tags_berita t2 ON t1.id = t2.berita_id WHERE t2.tags_id = :tags ", nativeQuery = true)
    List<Berita> getAllByTags(@Param("tags") Long tagsId);

    @Query(value = "SELECT * FROM berita  WHERE category = :category", nativeQuery = true)
    List<Berita> getAllByCategory(String category);

    @Query("SELECT SUBSTRING(b.judulBerita, 1, LOCATE(' ', b.judulBerita) - 1) FROM Berita b WHERE b.id = :id")
    String getByIdBerita(Long id);

    @Query(value = "SELECT * FROM berita WHERE judul_berita LIKE %:judul% LIMIT 4", nativeQuery = true)
    List<Berita> relatedPost(@Param("judul") String judul);

    @Query(value = "SELECT * FROM berita WHERE category_id = :categoryId ORDER BY updated_date DESC LIMIT 5", nativeQuery = true)
    List<Berita> terbaruByCategory(Long categoryId);

}

