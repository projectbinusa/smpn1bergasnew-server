package com.smpn1.bergas.repository;

import com.smpn1.bergas.model.Sarana;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SaranaRepository extends JpaRepository<Sarana, Long> {
    @Query(value = "SELECT * FROM sarana ORDER BY updated_date DESC", nativeQuery = true)
    Page<Sarana> getAll(Pageable pageable);

    @Query("SELECT s FROM Sarana s WHERE s.category = :category AND s.userId = :userId")
    Page<Sarana> getAllByCategory(@Param("userId") Long userId, @Param("category") String category, Pageable pageable);

    Page<Sarana> findByUserIdOrderByUpdatedDateDesc(
            Long userId,
            Pageable pageable);
}
