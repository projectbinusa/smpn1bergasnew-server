package com.smpn1.bergas.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smpn1.bergas.model.CategoryGalery;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.CategoryGaleryService;

@RestController
@RequestMapping("/api/category_galery")
@CrossOrigin(origins = "*")
public class CategoryGaleryController {
    @Autowired
    private CategoryGaleryService categoryGaleryService;

    @PostMapping(path = "/add")
    public ResponseEntity<CommonResponse<CategoryGalery>> add(@RequestBody CategoryGalery categoryprogram) throws SQLException, ClassNotFoundException {
        CommonResponse<CategoryGalery> response = new CommonResponse<>();
        try {
            CategoryGalery categoryprogram1 = categoryGaleryService.add(categoryprogram);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(categoryprogram1);
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
    public ResponseEntity<CommonResponse<Page<CategoryGalery>>> listAllCategoryGalery(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<CategoryGalery>> response = new CommonResponse<>();
        try {
            Page<CategoryGalery> beritaPage = categoryGaleryService.getAll(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" CategoryGalery list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve guru list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all/no_page")
    public ResponseEntity<CommonResponse<List<CategoryGalery>>> getAllCategoryGalery(
    ) {

        CommonResponse<List<CategoryGalery>> response = new CommonResponse<>();
        try {
            List<CategoryGalery> beritaPage = categoryGaleryService.getAllNoPage();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" CategoryGalery list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Page<CategoryGalery>>> listAllCategoryGaleryTerbaru(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<CategoryGalery>> response = new CommonResponse<>();
        try {
            Page<CategoryGalery> beritaPage = categoryGaleryService.getAllTerbaru(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" CategoryGalery list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<CategoryGalery>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<CategoryGalery> response = new CommonResponse<>();
        try {
            CategoryGalery categoryBerita = categoryGaleryService.getById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("CategoryGalery get successfully.");
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
    public ResponseEntity<CommonResponse<CategoryGalery>> updateCategoryGalery(@PathVariable("id") Long id, @RequestBody CategoryGalery categoryprogram) throws SQLException, ClassNotFoundException {
        CommonResponse<CategoryGalery> response = new CommonResponse<>();
        try {
            CategoryGalery tabelDip = categoryGaleryService.edit(categoryprogram, id);
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
        return ResponseEntity.ok(categoryGaleryService.delete(id));
    }
}
