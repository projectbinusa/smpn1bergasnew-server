package com.smpn1.bergas.controller;


import com.smpn1.bergas.config.JwtTokenUtil;
import com.smpn1.bergas.model.Berita;
import com.smpn1.bergas.model.Jenjang;
import com.smpn1.bergas.model.Jenjang;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.JenjangService;
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
@RequestMapping("/api/jenjang")
@CrossOrigin(origins = "*")
public class JenjangController {
    @Autowired
    private JenjangService jenjangService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping(path = "/add")
    public ResponseEntity<CommonResponse<Jenjang>> add(@RequestBody Jenjang jenjang) throws SQLException, ClassNotFoundException {
        CommonResponse<Jenjang> response = new CommonResponse<>();
        try {
            Jenjang jenjang1 = jenjangService.add(jenjang);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(jenjang1);
            response.setMessage("Jenjang created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create jenjang: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse<Page<Jenjang>>> listAllJenjang(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Jenjang>> response = new CommonResponse<>();
        try {
            Page<Jenjang> beritaPage = jenjangService.getAll(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Jenjang list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Page<Jenjang>>> listAllJenjang(
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

        CommonResponse<Page<Jenjang>> response = new CommonResponse<>();

        try {
            Page<Jenjang> jenjangPage = jenjangService.findAllWithPaginationByUserId(
                    userId,
                    pageable);

            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(jenjangPage);
            response.setMessage("Jenjang list retrieved successfully.");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve jenjang list: " + e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all/terbaru")
    public ResponseEntity<CommonResponse<Page<Jenjang>>> listAllJenjangTerbaru(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Jenjang>> response = new CommonResponse<>();
        try {
            Page<Jenjang> beritaPage = jenjangService.getAllTerbaru(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Jenjang list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Jenjang>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<Jenjang> response = new CommonResponse<>();
        try {
            Jenjang categoryBerita = jenjangService.getById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("Jenjang get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get jenjang: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "/put/{id}", produces = "application/json")
    public ResponseEntity<CommonResponse<Jenjang>> updateJenjang(@PathVariable("id") Long id, @RequestBody Jenjang jenjang) throws SQLException, ClassNotFoundException {
        CommonResponse<Jenjang> response = new CommonResponse<>();
        try {
            Jenjang tabelDip = jenjangService.edit(jenjang, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage(" Jenjang updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update jenjang : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(jenjangService.delete(id));
    }

    @GetMapping(path = "/get/by-link/{link}")
    public ResponseEntity<CommonResponse<Jenjang>> getByLink(@PathVariable("link") String link) {
        CommonResponse<Jenjang> response = new CommonResponse<>();
        try {
            Jenjang jenjang = jenjangService.getByLink(link);
            if (jenjang != null) {
                response.setStatus("success");
                response.setCode(HttpStatus.OK.value());
                response.setData(jenjang);
                response.setMessage("Jenjang retrieved successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatus("error");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("Jenjang not found.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Failed to get jenjang: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
