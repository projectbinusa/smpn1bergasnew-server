package com.smpn1.bergas.controller;


import com.smpn1.bergas.model.Prestasi;
import com.smpn1.bergas.model.Prestasi;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.PrestasiService;
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
@RequestMapping("/smpn1bergas/api/prestasi")
@CrossOrigin(origins = "*")
public class PrestasiController {
    @Autowired
    private PrestasiService prestasiService;

    @PostMapping(path = "/add", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Prestasi>> add(Prestasi prestasi, @RequestPart("file") MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<Prestasi> response = new CommonResponse<>();
        try {
            Prestasi prestasi1 = prestasiService.add(prestasi, multipartFile);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(prestasi1);
            response.setMessage("Prestasi created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create prestasi: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse<Page<Prestasi>>> listAllPrestasi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Prestasi>> response = new CommonResponse<>();
        try {
            Page<Prestasi> beritaPage = prestasiService.getAll(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Prestasi list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Page<Prestasi>>> listAllPrestasiTerbaru(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Prestasi>> response = new CommonResponse<>();
        try {
            Page<Prestasi> beritaPage = prestasiService.getAllTerbaru(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Prestasi list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Prestasi>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<Prestasi> response = new CommonResponse<>();
        try {
            Prestasi categoryBerita = prestasiService.getByid(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("Prestasi get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get prestasi: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "/put/{id}")
    public ResponseEntity<CommonResponse<Prestasi>> updatePrestasi(@PathVariable("id") Long id, @RequestBody Prestasi prestasi) throws SQLException, ClassNotFoundException {
        CommonResponse<Prestasi> response = new CommonResponse<>();
        try {
            Prestasi tabelDip = prestasiService.edit(prestasi, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Prestasi updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update prestasi : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "/put/foto/{id}", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Prestasi>> updatePrestasi(@PathVariable("id") Long id,  @RequestPart("file") MultipartFile multipartFile ) throws SQLException, ClassNotFoundException {
        CommonResponse<Prestasi> response = new CommonResponse<>();
        try {
            Prestasi tabelDip = prestasiService.editFoto( multipartFile, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Prestasi updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update prestasi : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(prestasiService.delete(id));
    }
}
