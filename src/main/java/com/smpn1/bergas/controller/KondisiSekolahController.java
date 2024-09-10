package com.smpn1.bergas.controller;

import com.smpn1.bergas.model.KondisiSekolah;
import com.smpn1.bergas.model.KondisiSekolah;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.KondisiSekolahService;
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
@RequestMapping("/smpn1bergas/api/kondisi_sekolah")
@CrossOrigin(origins = "*")
public class KondisiSekolahController {
    @Autowired
    private KondisiSekolahService kondisiSekolahService;

    @PostMapping(path = "/add", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<KondisiSekolah>> add(KondisiSekolah kondisiSekolah, @RequestPart("file") MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
        CommonResponse<KondisiSekolah> response = new CommonResponse<>();
        try {
            KondisiSekolah kondisiSekolah1 = kondisiSekolahService.add(kondisiSekolah, multipartFile);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(kondisiSekolah1);
            response.setMessage("Kondisi sekolah created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create kondisi sekolah: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse<Page<KondisiSekolah>>> listAllKondisiSekolah(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<KondisiSekolah>> response = new CommonResponse<>();
        try {
            Page<KondisiSekolah> beritaPage = kondisiSekolahService.getAll(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Kondisi sekolah list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve kondisi sekolah list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all/terbaru")
    public ResponseEntity<CommonResponse<Page<KondisiSekolah>>> listAllKondisiSekolahTerbaru(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<KondisiSekolah>> response = new CommonResponse<>();
        try {
            Page<KondisiSekolah> beritaPage = kondisiSekolahService.getAllTerbaru(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" KondisiSekolah list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<KondisiSekolah>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<KondisiSekolah> response = new CommonResponse<>();
        try {
            KondisiSekolah categoryBerita = kondisiSekolahService.getByid(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("Kondisi sekolah get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get kondisi sekolah: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "/put/{id}")
    public ResponseEntity<CommonResponse<KondisiSekolah>> updateKondisiSekolah(@PathVariable("id") Long id,@RequestBody KondisiSekolah kondisiSekolah) throws SQLException, ClassNotFoundException {
        CommonResponse<KondisiSekolah> response = new CommonResponse<>();
        try {
            KondisiSekolah tabelDip = kondisiSekolahService.edit(kondisiSekolah,  id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Kondisi sekolah updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update kondisi sekolah : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "/put/foto/{id}", consumes = "multipart/form-data")
    public ResponseEntity<CommonResponse<KondisiSekolah>> updateKondisiSekolah(@PathVariable("id") Long id,  @RequestPart("file") MultipartFile multipartFile ) throws SQLException, ClassNotFoundException {
        CommonResponse<KondisiSekolah> response = new CommonResponse<>();
        try {
            KondisiSekolah tabelDip = kondisiSekolahService.editFoto( multipartFile, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Kondisi sekolah updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update kondisi sekolah : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(kondisiSekolahService.delete(id));
    }
}
