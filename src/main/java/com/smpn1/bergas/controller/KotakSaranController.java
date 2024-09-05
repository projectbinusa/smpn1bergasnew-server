package com.smpn1.bergas.controller;

import com.smpn1.bergas.DTO.KotakSaranDTO;
import com.smpn1.bergas.model.KotakSaran;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.KotakSaranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@RestController
@RequestMapping("/smpn1bergas/api/kotak_saran")
@CrossOrigin(origins = "*")
public class KotakSaranController {
    @Autowired
    private KotakSaranService kotakSaranService;


    @PostMapping(path = "/add")
    public ResponseEntity<CommonResponse<KotakSaran>> add(@RequestBody KotakSaranDTO kotakSaran) throws SQLException, ClassNotFoundException {
        CommonResponse<KotakSaran> response = new CommonResponse<>();
        try {
            KotakSaran kotakSaran1 = kotakSaranService.add(kotakSaran);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(kotakSaran1);
            response.setMessage("KotakSaran created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create kotakSaran: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse<Page<KotakSaran>>> listAllKotakSaran(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<KotakSaran>> response = new CommonResponse<>();
        try {
            Page<KotakSaran> beritaPage = kotakSaranService.getAll(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" KotakSaran list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Page<KotakSaran>>> listAllKotakSaranTerbaru(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<KotakSaran>> response = new CommonResponse<>();
        try {
            Page<KotakSaran> beritaPage = kotakSaranService.getAllTerbaru(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" KotakSaran list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<KotakSaran>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<KotakSaran> response = new CommonResponse<>();
        try {
            KotakSaran categoryBerita = kotakSaranService.getById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("KotakSaran get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get kotakSaran: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "/put/{id}", produces = "application/json")
    public ResponseEntity<CommonResponse<KotakSaran>> updateKotakSaran(@PathVariable("id") Long id, @RequestBody KotakSaran kotakSaran) throws SQLException, ClassNotFoundException {
        CommonResponse<KotakSaran> response = new CommonResponse<>();
        try {
            KotakSaran tabelDip = kotakSaranService.edit(kotakSaran, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Kotak Saran updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update kotak saran : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(kotakSaranService.delete(id));
    }
}
