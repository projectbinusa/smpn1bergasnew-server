package com.smpn1.bergas.controller;


import com.smpn1.bergas.DTO.FotoKegiatanDTO;
import com.smpn1.bergas.model.FotoKegiatan;
import com.smpn1.bergas.model.Kegiatan;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.FotoKegiatanService;
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
@RequestMapping("/smpn1bergas/api/foto_kegiatan")
@CrossOrigin(origins = "*")
public class FotoKegiatanController {
    @Autowired
    private FotoKegiatanService fotoKegiatanService;

    @PostMapping(path = "/add", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<FotoKegiatan>> add(FotoKegiatanDTO fotoKegiatan, @RequestPart("file") MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<FotoKegiatan> response = new CommonResponse<>();
        try {
            FotoKegiatan fotoKegiatan1 = fotoKegiatanService.add(fotoKegiatan, multipartFile);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(fotoKegiatan1);
            response.setMessage("FotoKegiatan created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create fotoKegiatan: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse<Page<FotoKegiatan>>> listAllFotoKegiatan(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<FotoKegiatan>> response = new CommonResponse<>();
        try {
            Page<FotoKegiatan> beritaPage = fotoKegiatanService.getAll(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" FotoKegiatan list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve fotoKegiatan list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all/terbaru")
    public ResponseEntity<CommonResponse<Page<FotoKegiatan>>> listAllFotoKegiatanTerbaru(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<FotoKegiatan>> response = new CommonResponse<>();
        try {
            Page<FotoKegiatan> beritaPage = fotoKegiatanService.getAllTerbaru(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" FotoKegiatan list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve guru list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all/by_id_kegiatan")
    public ResponseEntity<CommonResponse<Page<FotoKegiatan>>> listAllFotoKegiatanByIdKegiatan(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam("id_kegiatan") Long id
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<FotoKegiatan>> response = new CommonResponse<>();
        try {
            Page<FotoKegiatan> beritaPage = fotoKegiatanService.getAllByKegiatan(id,pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" FotoKegiatan list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<FotoKegiatan>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<FotoKegiatan> response = new CommonResponse<>();
        try {
            FotoKegiatan categoryBerita = fotoKegiatanService.getById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("Foto Sarana get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get foto sarana: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "/put/{id}")
    public ResponseEntity<CommonResponse<FotoKegiatan>> updateFotoKegiatan(@PathVariable("id") Long id, @RequestBody FotoKegiatanDTO fotoKegiatan) throws SQLException, ClassNotFoundException {
        CommonResponse<FotoKegiatan> response = new CommonResponse<>();
        try {
            FotoKegiatan fotoKegiatan1 = fotoKegiatanService.edit(fotoKegiatan, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(fotoKegiatan1);
            response.setMessage("FotoKegiatan updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update fotoKegiatan : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "/put/{id}", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<FotoKegiatan>> updateFotoKegiatan(@PathVariable("id") Long id, @RequestPart("file") MultipartFile multipartFile ) throws SQLException, ClassNotFoundException {
        CommonResponse<FotoKegiatan> response = new CommonResponse<>();
        try {
            FotoKegiatan fotoKegiatan1 = fotoKegiatanService.editFoto( multipartFile, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(fotoKegiatan1);
            response.setMessage("FotoKegiatan updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update fotoKegiatan : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(fotoKegiatanService.delete(id));
    }
}
