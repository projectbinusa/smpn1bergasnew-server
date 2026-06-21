package com.smpn1.bergas.controller;

import com.smpn1.bergas.config.JwtTokenUtil;
import com.smpn1.bergas.model.Berita;
import com.smpn1.bergas.model.Perpustakaan;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.PerpustakaanService;
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
@RequestMapping("/api/perpustakaan")
@CrossOrigin(origins = "*")
public class PerpustakaanController {
    @Autowired
    private PerpustakaanService perpustakaanService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private DomainUtil domainUtil;

    @PostMapping(path = "/add", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Perpustakaan>> add(Perpustakaan perpustakaan,
            @RequestPart("file") MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<Perpustakaan> response = new CommonResponse<>();
        try {
            Perpustakaan perpustakaan1 = perpustakaanService.add(perpustakaan, multipartFile);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(perpustakaan1);
            response.setMessage("Perpustakaan created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create perpustakaan: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse<Page<Perpustakaan>>> listAllPerpustakaan(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Perpustakaan>> response = new CommonResponse<>();
        try {
            Page<Perpustakaan> beritaPage = perpustakaanService.getAll(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Perpustakaan list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Page<Perpustakaan>>> listAllPerpustakaan(
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

        CommonResponse<Page<Perpustakaan>> response = new CommonResponse<>();

        try {
            Page<Perpustakaan> perpustakaanPage = perpustakaanService.findAllWithPaginationByUserId(
                    userId,
                    pageable);

            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(perpustakaanPage);
            response.setMessage("Perpustakaan list retrieved successfully.");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve perpustakaan list: " + e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all/terbaru")
    public ResponseEntity<CommonResponse<Page<Perpustakaan>>> listAllPerpustakaanTerbaru(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Perpustakaan>> response = new CommonResponse<>();
        try {
            Long userId = domainUtil.getCurrentUserId(request);

            Page<Perpustakaan> beritaPage = perpustakaanService.getAllTerbaru(userId, pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Perpustakaan list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Perpustakaan>> get(@PathVariable("id") long id)
            throws SQLException, ClassNotFoundException {
        CommonResponse<Perpustakaan> response = new CommonResponse<>();
        try {
            Perpustakaan categoryBerita = perpustakaanService.getByid(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("Perpustakaan get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get perpustakaan: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/put/{id}")
    public ResponseEntity<CommonResponse<Perpustakaan>> updatePerpustakaan(@PathVariable("id") Long id,
            @RequestBody Perpustakaan perpustakaan) throws SQLException, ClassNotFoundException {
        CommonResponse<Perpustakaan> response = new CommonResponse<>();
        try {
            Perpustakaan tabelDip = perpustakaanService.edit(perpustakaan, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Perpustakaan updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update perpustakaan : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/put/foto/{id}")
    public ResponseEntity<CommonResponse<Perpustakaan>> updatePerpustakaan(@PathVariable("id") Long id,
            @RequestPart("file") MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<Perpustakaan> response = new CommonResponse<>();
        try {
            Perpustakaan tabelDip = perpustakaanService.editFoto(multipartFile, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Perpustakaan updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update perpustakaan : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(perpustakaanService.delete(id));
    }
}
