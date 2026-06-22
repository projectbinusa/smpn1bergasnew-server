package com.smpn1.bergas.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smpn1.bergas.model.Domain;
import com.smpn1.bergas.repository.DomainRepository;

@Component
public class DomainUtil {

    @Autowired
    private DomainRepository domainRepository;

    public Long getCurrentUserId(HttpServletRequest request) {

        // String domainName = request.getServerName();
        String domainName = request.getHeader("Origin");
        if (domainName == null) {
            domainName = request.getHeader("Referer");
        }

        System.out.println("DOMAIN AKTIF = " + domainName);

        Domain domain = domainRepository.findByName(domainName);

        if (domain == null) {
            throw new RuntimeException(
                    "Domain tidak terdaftar. Domain = " + domainName);
        }

        return domain.getUserId();
    }
}
