package com.smpn1.bergas.controller;

import com.smpn1.bergas.model.Galeri;
import com.smpn1.bergas.model.Galeri;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.GaleriService;

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

import com.smpn1.bergas.DTO.GaleriDTO;

@RestController
@RequestMapping("/api/galeri")
@CrossOrigin(origins = "*")
public class GaleriController {

    @Autowired
    private GaleriService galeriService;

    @PostMapping(path = "/add", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Galeri>> addGaleri(
            @RequestPart("galeri") GaleriDTO galeri,
            @RequestPart("files") MultipartFile[] files) {
        CommonResponse<Galeri> response = new CommonResponse<>();
        try {
            Galeri result = galeriService.add(galeri, files);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(result);
            response.setMessage("Galeri created successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // @PostMapping(path = "/add", consumes = "multipart/form-data")
    // public ResponseEntity<CommonResponse<Galeri>> addGaleri(
    //         @RequestPart("galeri") Galeri galeri,
    //         @RequestPart("files") MultipartFile[] files) {
    //     CommonResponse<Galeri> response = new CommonResponse<>();
    //     try {
    //         Galeri result = galeriService.add(galeri, files);
    //         response.setStatus("success");
    //         response.setCode(HttpStatus.CREATED.value());
    //         response.setData(result);
    //         response.setMessage("Galeri created successfully.");
    //         return ResponseEntity.status(HttpStatus.CREATED).body(response);
    //     } catch (Exception e) {
    //         response.setStatus("error");
    //         response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    //         response.setMessage("Failed: " + e.getMessage());
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    //     }
    // }
    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse<Page<Galeri>>> listAllGaleri(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Galeri>> response = new CommonResponse<>();
        try {
            Page<Galeri> beritaPage = galeriService.getAll(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Galeri list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve galeri list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all/terbaru")
    public ResponseEntity<CommonResponse<Page<Galeri>>> listAllGaleriTerbaru(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Galeri>> response = new CommonResponse<>();
        try {
            Page<Galeri> beritaPage = galeriService.getAllTerbaru(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Galeri list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Galeri>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<Galeri> response = new CommonResponse<>();
        try {
            Galeri categoryBerita = galeriService.getById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("Galeri get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get galeri: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/put/{id}", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Galeri>> updateGaleri(
            @PathVariable("id") Long id,
            @RequestPart("data") GaleriDTO galeriDTO, // JSON data
            @RequestPart(value = "files", required = false) MultipartFile[] files // optional
    ) {
        CommonResponse<Galeri> response = new CommonResponse<>();
        try {
            Galeri updatedGaleri = galeriService.edit(galeriDTO, id, files);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(updatedGaleri);
            response.setMessage("Galeri berhasil diupdate.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Gagal update galeri: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // @PutMapping(path = "/put/{id}")
    // public ResponseEntity<CommonResponse<Galeri>> updateGaleri(@PathVariable("id") Long id,@RequestBody GaleriDTO galeri ) throws SQLException, ClassNotFoundException {
    //     CommonResponse<Galeri> response = new CommonResponse<>();
    //     try {
    //         Galeri tabelDip = galeriService.edit(galeri, id);
    //         response.setStatus("success");
    //         response.setCode(HttpStatus.OK.value());
    //         response.setData(tabelDip);
    //         response.setMessage("Galeri updated successfully.");
    //         return new ResponseEntity<>(response, HttpStatus.OK);
    //     } catch (Exception e) {
    //         response.setStatus("error");
    //         response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    //         response.setData(null);
    //         response.setMessage("Failed to update galeri : " + e.getMessage());
    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
    @PutMapping(path = "/put/foto/{id}", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<Galeri>> updateFoto(
            @PathVariable("id") Long id,
            @RequestPart("files") MultipartFile[] files) {
        CommonResponse<Galeri> response = new CommonResponse<>();
        try {
            Galeri result = galeriService.editFoto(files, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(result);
            response.setMessage("Foto galeri berhasil diupdate.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(galeriService.delete(id));
    }
}
