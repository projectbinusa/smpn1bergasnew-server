package com.smpn1.bergas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smpn1.bergas.model.Domain;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {

    Domain findByName(String name);

}