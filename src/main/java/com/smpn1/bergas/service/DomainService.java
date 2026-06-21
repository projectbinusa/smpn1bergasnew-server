package com.smpn1.bergas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smpn1.bergas.model.Domain;
import com.smpn1.bergas.repository.DomainRepository;

@Service
public class DomainService {

    @Autowired
    private DomainRepository domainRepository;

    public Long getUserIdByDomain(String domainName) {

        Domain domain = domainRepository.findByName(domainName);

        if (domain == null) {
            throw new RuntimeException(
                    "Domain tidak ditemukan : " + domainName);
        }

        return domain.getUserId();
    }
}