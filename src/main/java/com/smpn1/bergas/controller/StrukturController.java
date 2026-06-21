package com.smpn1.bergas.controller;

import com.smpn1.bergas.DTO.StrukturDTO;
import com.smpn1.bergas.config.JwtTokenUtil;
import com.smpn1.bergas.model.Berita;
import com.smpn1.bergas.model.Struktur;
import com.smpn1.bergas.model.Struktur;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.StrukturService;
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
@RequestMapping("/api/struktur")
@CrossOrigin(origins = "*")
public class StrukturController {
    @Autowired
    private StrukturService strukturService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private DomainUtil domainUtil;

    @PostMapping(path = "/add", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Struktur>> add(
            @RequestPart("struktur") Struktur struktur,
            @RequestPart("files") MultipartFile[] files) {

        CommonResponse<Struktur> response = new CommonResponse<>();
        try {
            Struktur result = strukturService.add(struktur, files);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(result);
            response.setMessage("Berita created successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse<Page<Struktur>>> listAllStruktur(
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

        CommonResponse<Page<Struktur>> response = new CommonResponse<>();
        try {
            Long userId = domainUtil.getCurrentUserId(request);
            Page<Struktur> beritaPage = strukturService.getAll(userId, pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Struktur list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve struktur list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/admin/all")
    public ResponseEntity<CommonResponse<Page<Struktur>>> listAdminAllStruktur(
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

        CommonResponse<Page<Struktur>> response = new CommonResponse<>();

        try {
            Page<Struktur> strukturPage = strukturService.findAllWithPaginationByUserId(
                    userId,
                    pageable);

            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(strukturPage);
            response.setMessage("Struktur list retrieved successfully.");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve struktur list: " + e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all/terbaru")
    public ResponseEntity<CommonResponse<Page<Struktur>>> listAllStrukturTerbaru(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Struktur>> response = new CommonResponse<>();
        try {
            Page<Struktur> beritaPage = strukturService.getAllTerbaru(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Struktur list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve guru list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/jenis")
    public ResponseEntity<CommonResponse<Page<Struktur>>> getByJenis(
            @RequestParam(name = "jenis") String idKategory,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Struktur>> response = new CommonResponse<>();
        try {
            Page<Struktur> strukturPage = strukturService.getByJenis(idKategory, pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(strukturPage);
            response.setMessage(" Struktur list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve struktur list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<Struktur>> get(@PathVariable("id") long id)
            throws SQLException, ClassNotFoundException {
        CommonResponse<Struktur> response = new CommonResponse<>();
        try {
            Struktur categoryBerita = strukturService.findById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("Struktur get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get struktur: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/put/{id}")
    public ResponseEntity<CommonResponse<Struktur>> updateStruktur(@PathVariable("id") Long id,
            @RequestBody StrukturDTO struktur) throws SQLException, ClassNotFoundException {
        CommonResponse<Struktur> response = new CommonResponse<>();
        try {
            Struktur tabelDip = strukturService.edit(id, struktur);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Struktur updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update struktur : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/put/foto/{id}", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Struktur>> updateStruktur(@PathVariable("id") Long id,
            @RequestPart("file") MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<Struktur> response = new CommonResponse<>();
        try {
            Struktur tabelDip = strukturService.editFoto(id, multipartFile);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Struktur updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update struktur : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(strukturService.delete(id));
    }
}
