package com.smpn1.bergas.controller;


import com.smpn1.bergas.DTO.FotoSaranaDTO;
import com.smpn1.bergas.model.FotoSarana;
import com.smpn1.bergas.model.FotoSarana;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.FotoSaranaService;
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
@RequestMapping("/smpn1bergas/api/foto_sarana")
@CrossOrigin(origins = "*")
public class FotoSaranaController {
    @Autowired
    private FotoSaranaService fotoSaranaService;

    @PostMapping(path = "/add", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<FotoSarana>> add(FotoSaranaDTO fotoSarana, @RequestPart("file") MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<FotoSarana> response = new CommonResponse<>();
        try {
            FotoSarana fotoSarana1 = fotoSaranaService.add(fotoSarana, multipartFile);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(fotoSarana1);
            response.setMessage("FotoSarana created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create fotoSarana: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse<Page<FotoSarana>>> listAllFotoSarana(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<FotoSarana>> response = new CommonResponse<>();
        try {
            Page<FotoSarana> beritaPage = fotoSaranaService.getAll(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" FotoSarana list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve fotoSarana list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all/terbaru")
    public ResponseEntity<CommonResponse<Page<FotoSarana>>> listAllFotoSaranaTerbaru(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<FotoSarana>> response = new CommonResponse<>();
        try {
            Page<FotoSarana> beritaPage = fotoSaranaService.getAllTerbaru(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" FotoSarana list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve guru list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all/by_id_sarana")
    public ResponseEntity<CommonResponse<Page<FotoSarana>>> listAllFotoSaranaByIdSarana(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam("id_sarana") Long id
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<FotoSarana>> response = new CommonResponse<>();
        try {
            Page<FotoSarana> beritaPage = fotoSaranaService.getAllBySarana(id,pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" FotoSarana list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<FotoSarana>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<FotoSarana> response = new CommonResponse<>();
        try {
            FotoSarana categoryBerita = fotoSaranaService.getById(id);
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
    public ResponseEntity<CommonResponse<FotoSarana>> updateFotoSarana(@PathVariable("id") Long id,@RequestBody FotoSaranaDTO fotoSarana ) throws SQLException, ClassNotFoundException {
        CommonResponse<FotoSarana> response = new CommonResponse<>();
        try {
            FotoSarana fotoSarana1 = fotoSaranaService.edit(fotoSarana, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(fotoSarana1);
            response.setMessage("FotoSarana updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update fotoSarana : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "/put/foto/{id}", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<FotoSarana>> updateFotoSarana(@PathVariable("id") Long id, @RequestPart("file") MultipartFile multipartFile ) throws SQLException, ClassNotFoundException {
        CommonResponse<FotoSarana> response = new CommonResponse<>();
        try {
            FotoSarana fotoSarana1 = fotoSaranaService.editFoto(multipartFile, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(fotoSarana1);
            response.setMessage("FotoSarana updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update fotoSarana : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(fotoSaranaService.delete(id));
    }
}
