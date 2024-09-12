package com.smpn1.bergas.controller;


import com.smpn1.bergas.model.Ekstrakurikuler;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.EkstrakurikulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.Map;

@RestController
@RequestMapping("/smpn1bergas/api/ekstrakulikuler")
@CrossOrigin(origins = "*")
public class EkstrakulikulerController {
    @Autowired
    private EkstrakurikulerService ekstrakurikulerService;

    @PostMapping(path = "/add")
    public ResponseEntity<CommonResponse<Ekstrakurikuler>> add(@RequestBody Ekstrakurikuler ekstrakurikuler ) throws SQLException, ClassNotFoundException {
        CommonResponse<Ekstrakurikuler> response = new CommonResponse<>();
        try {
            Ekstrakurikuler ekstrakurikuler1 = ekstrakurikulerService.add(ekstrakurikuler );
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(ekstrakurikuler1);
            response.setMessage("Ekstrakurikuler created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create ekstrakurikuler: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<CommonResponse<Page<Ekstrakurikuler>>> listAlEkstrakurikuler(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sort,
            @RequestParam(defaultValue = "asc") String direction) throws SQLException, ClassNotFoundException {
        CommonResponse<Page<Ekstrakurikuler>> response = new CommonResponse<>();
        try {
            // Membuat objek Pageable untuk digunakan dalam repository.findAll
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));

            // Menggunakan service.findAll dengan pageable
            Page<Ekstrakurikuler> categoryBeritas = ekstrakurikulerService.getAll(pageable);

            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBeritas);
            response.setMessage("Category keuangan list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve category keuangan list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all/terbaru")
    public ResponseEntity<CommonResponse<Page<Ekstrakurikuler>>> listAllEkstrakurikulerTerbaru(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Ekstrakurikuler>> response = new CommonResponse<>();
        try {
            Page<Ekstrakurikuler> beritaPage = ekstrakurikulerService.getAllTerbaru(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Ekstrakurikuler list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Ekstrakurikuler>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<Ekstrakurikuler> response = new CommonResponse<>();
        try {
            Ekstrakurikuler categoryBerita = ekstrakurikulerService.getById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("Ekstrakurikuler get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get ekstrakulikuler: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "/put/{id}")
    public ResponseEntity<CommonResponse<Ekstrakurikuler>> updateEkstrakurikuler(@PathVariable("id") Long id, @RequestBody Ekstrakurikuler ekstrakurikuler) throws SQLException, ClassNotFoundException {
        CommonResponse<Ekstrakurikuler> response = new CommonResponse<>();
        try {
            Ekstrakurikuler tabelDip = ekstrakurikulerService.edit(ekstrakurikuler, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Ekstrakurikuler updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update ekstrakurikuler : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "/put/foto/{id}", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Ekstrakurikuler>> updateFoto(@PathVariable("id") Long id,@RequestPart("file") MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<Ekstrakurikuler> response = new CommonResponse<>();
        try {
            Ekstrakurikuler tabelDip = ekstrakurikulerService.editFoto( id, multipartFile);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Ekstrakurikuler updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update ekstrakurikuler : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ekstrakurikulerService.delete(id));
    }
}
