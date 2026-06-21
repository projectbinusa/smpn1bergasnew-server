package com.smpn1.bergas.controller;


import com.smpn1.bergas.config.JwtTokenUtil;
import com.smpn1.bergas.model.Berita;
import com.smpn1.bergas.model.Sejarah;
import com.smpn1.bergas.model.Sejarah;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.SejarahService;
import com.smpn1.bergas.util.DomainUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/api/sejarah")
@CrossOrigin(origins = "*")
public class SejarahController {
    @Autowired
    private SejarahService sejarahService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private DomainUtil domainUtil;

    @PostMapping(path = "/add")
    public ResponseEntity<CommonResponse<Sejarah>> add(@RequestBody Sejarah sejarah) throws SQLException, ClassNotFoundException {
        CommonResponse<Sejarah> response = new CommonResponse<>();
        try {
            Sejarah sejarah1 = sejarahService.add(sejarah);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(sejarah1);
            response.setMessage("Sejarah created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create sejarah: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse<Page<Sejarah>>> listAllSejarah(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Sejarah>> response = new CommonResponse<>();
        try {
            Page<Sejarah> beritaPage = sejarahService.getAll(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Sejarah list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve guru list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     @GetMapping(path = "/admin/all")
    public ResponseEntity<CommonResponse<Page<Sejarah>>> listAllSejarah(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtTokenUtil.getUserIdFromToken(token);

        Pageable pageable;
        if (sortOrder.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        CommonResponse<Page<Sejarah>> response = new CommonResponse<>();

        try {
            Page<Sejarah> sejarahPage = sejarahService.findAllWithPaginationByUserId(
                    userId,
                    pageable);

            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(sejarahPage);
            response.setMessage("Sejarah list retrieved successfully.");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve sejarah list: " + e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all/terbaru")
    public ResponseEntity<CommonResponse<Page<Sejarah>>> listAllSejarahTerbaru(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder) {

        Pageable pageable;
        if (sortOrder.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        CommonResponse<Page<Sejarah>> response = new CommonResponse<>();
        try {
            Long userId = domainUtil.getCurrentUserId(request);
            Page<Sejarah> sejarahPage = sejarahService.getAllTerbaru(userId, pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(sejarahPage);
            response.setMessage(" Sejarah list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve guru list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<Sejarah>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<Sejarah> response = new CommonResponse<>();
        try {
            Sejarah categoryBerita = sejarahService.getById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("Sejarah get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get sejarah: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "/put/{id}", produces = "application/json")
    public ResponseEntity<CommonResponse<Sejarah>> updateSejarah(@PathVariable("id") Long id, @RequestBody Sejarah sejarah) throws SQLException, ClassNotFoundException {
        CommonResponse<Sejarah> response = new CommonResponse<>();
        try {
            Sejarah tabelDip = sejarahService.edit(sejarah, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage(" Sejarah updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update sejarah : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(sejarahService.delete(id));
    }
}
