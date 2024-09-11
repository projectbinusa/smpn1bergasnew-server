package com.smpn1.bergas.controller;


import com.smpn1.bergas.model.Alumni;
import com.smpn1.bergas.model.Kontak;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.AlumniService;
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

@RestController
@RequestMapping("/smpn1bergas/api/alumni")
@CrossOrigin(origins = "*")
public class AlumniController {
    @Autowired
    private AlumniService alumniService;

    @PostMapping(path = "/add")
    public ResponseEntity<CommonResponse<Alumni>> add(@RequestBody Alumni alumni) throws SQLException, ClassNotFoundException {
        CommonResponse<Alumni> response = new CommonResponse<>();
        try {
            Alumni prestasi1 = alumniService.add(alumni);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(prestasi1);
            response.setMessage("Alumni created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create alumni: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse<Page<Alumni>>> listAllAlumni(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Alumni>> response = new CommonResponse<>();
        try {
            Page<Alumni> beritaPage = alumniService.getAll(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Alumni list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve guru list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all/terbaru")
    public ResponseEntity<CommonResponse<Page<Alumni>>> listAllAlumniTerbaru(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Alumni>> response = new CommonResponse<>();
        try {
            Page<Alumni> beritaPage = alumniService.getAllTerbaru(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Alumni list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Alumni>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<Alumni> response = new CommonResponse<>();
        try {
            Alumni categoryBerita = alumniService.getById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("Alumni get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get alumni: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    @PutMapping(path = "/put/{id}", consumes = "multipart/form-data")
    @PutMapping(path = "/put/{id}")
    public ResponseEntity<CommonResponse<Alumni>> updateAlumni(@PathVariable("id") Long id, @RequestBody Alumni prestasi) throws SQLException, ClassNotFoundException {
        CommonResponse<Alumni> response = new CommonResponse<>();
        try {
            Alumni tabelDip = alumniService.edit(prestasi, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Alumni updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update alumni : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "/put/foto/{id}", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Alumni>> updateFoto(@PathVariable("id") Long id, @RequestPart("file") MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<Alumni> response = new CommonResponse<>();
        try {
            Alumni tabelDip = alumniService.editFoto( multipartFile, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Alumni updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update alumni : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(alumniService.delete(id));
    }
}
