package com.smpn1.bergas.controller;

import com.smpn1.bergas.model.VisiMisi;
import com.smpn1.bergas.model.VisiMisi;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.VisiMisiService;
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
@RequestMapping("/smpn1bergas/api/visiMisi")
@CrossOrigin(origins = "*")
public class VisiMisiController {
    @Autowired
    private VisiMisiService visiMisiService;

    @PostMapping(path = "/add")
    public ResponseEntity<CommonResponse<VisiMisi>> add(@RequestBody VisiMisi visiMisi) throws SQLException, ClassNotFoundException {
        CommonResponse<VisiMisi> response = new CommonResponse<>();
        try {
            VisiMisi visiMisi1 = visiMisiService.add(visiMisi);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(visiMisi1);
            response.setMessage("Visi Misi created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create Visi Misi: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse<Page<VisiMisi>>> listAllVisiMisi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<VisiMisi>> response = new CommonResponse<>();
        try {
            Page<VisiMisi> beritaPage = visiMisiService.getAll(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" VisiMisi list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Page<VisiMisi>>> listAllVisiMisiTerbaru(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<VisiMisi>> response = new CommonResponse<>();
        try {
            Page<VisiMisi> beritaPage = visiMisiService.getAllTerbaru(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" VisiMisi list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<VisiMisi>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<VisiMisi> response = new CommonResponse<>();
        try {
            VisiMisi categoryBerita = visiMisiService.getById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("VisiMisi get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get alumni: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "/put/{id}", produces = "application/json")
    public ResponseEntity<CommonResponse<VisiMisi>> updateVisiMisi(@PathVariable("id") Long id, @RequestBody VisiMisi visiMisi) throws SQLException, ClassNotFoundException {
        CommonResponse<VisiMisi> response = new CommonResponse<>();
        try {
            VisiMisi tabelDip = visiMisiService.edit(visiMisi, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Tabel Visi Misi updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update Visi Misi : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(visiMisiService.delete(id));
    }
}
