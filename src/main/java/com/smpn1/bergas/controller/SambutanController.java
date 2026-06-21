package com.smpn1.bergas.controller;

import com.smpn1.bergas.config.JwtTokenUtil;
import com.smpn1.bergas.model.Berita;
import com.smpn1.bergas.model.Sambutan;
import com.smpn1.bergas.model.Sambutan;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.SambutanService;
import com.smpn1.bergas.util.DomainUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/api/sambutan")
@CrossOrigin(origins = "*")
public class SambutanController {
    @Autowired
    private SambutanService sambutanService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private DomainUtil domainUtil;

    @PostMapping(path = "/add")
    public ResponseEntity<CommonResponse<Sambutan>> add(@RequestBody Sambutan sambutan)
            throws SQLException, ClassNotFoundException {
        CommonResponse<Sambutan> response = new CommonResponse<>();
        try {
            Sambutan sambutan1 = sambutanService.add(sambutan);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(sambutan1);
            response.setMessage("Sambutan created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create sambutan: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse<Page<Sambutan>>> listAllSambutan(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Sambutan>> response = new CommonResponse<>();
        try {
            Page<Sambutan> beritaPage = sambutanService.getAll(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Sambutan list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Page<Sambutan>>> listAllSambutan(
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

        CommonResponse<Page<Sambutan>> response = new CommonResponse<>();

        try {
            Page<Sambutan> sambutanPage = sambutanService.findAllWithPaginationByUserId(
                    userId,
                    pageable);

            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(sambutanPage);
            response.setMessage("Sambutan list retrieved successfully.");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve sambutan list: " + e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all/terbaru")
    public ResponseEntity<CommonResponse<Page<Sambutan>>> listAllSambutanTerbaru(
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

        CommonResponse<Page<Sambutan>> response = new CommonResponse<>();
        try {
            Long userId = domainUtil.getCurrentUserId(request);
            Page<Sambutan> beritaPage = sambutanService.getAllTerbaru(userId, pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Sambutan list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Sambutan>> get(@PathVariable("id") long id)
            throws SQLException, ClassNotFoundException {
        CommonResponse<Sambutan> response = new CommonResponse<>();
        try {
            Sambutan categoryBerita = sambutanService.getById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("Sambutan get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get sambutan: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/put/{id}")
    public ResponseEntity<CommonResponse<Sambutan>> updateSambutan(@PathVariable("id") Long id,
            @RequestBody Sambutan sambutan) throws SQLException, ClassNotFoundException {
        CommonResponse<Sambutan> response = new CommonResponse<>();
        try {
            Sambutan tabelDip = sambutanService.edit(sambutan, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Sambutan updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update sambutan : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/put/foto/{id}", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Sambutan>> updateSambutan(@PathVariable("id") Long id,
            @RequestPart("file") MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<Sambutan> response = new CommonResponse<>();
        try {
            Sambutan tabelDip = sambutanService.editFoto(multipartFile, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Sambutan updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update sambutan : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(sambutanService.delete(id));
    }
}
