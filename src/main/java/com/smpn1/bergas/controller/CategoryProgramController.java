package com.smpn1.bergas.controller;

import com.smpn1.bergas.model.CategoryProgram;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.CategoryProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/smpn1bergas/api/category_program")
@CrossOrigin(origins = "*")
public class CategoryProgramController {
    @Autowired
    private CategoryProgramService categoryprogramService;

    @PostMapping(path = "/add")
    public ResponseEntity<CommonResponse<CategoryProgram>> add(@RequestBody CategoryProgram categoryprogram) throws SQLException, ClassNotFoundException {
        CommonResponse<CategoryProgram> response = new CommonResponse<>();
        try {
            CategoryProgram categoryprogram1 = categoryprogramService.add(categoryprogram);
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
    public ResponseEntity<CommonResponse<Page<CategoryProgram>>> listAllCategoryProgram(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<CategoryProgram>> response = new CommonResponse<>();
        try {
            Page<CategoryProgram> beritaPage = categoryprogramService.getAll(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" CategoryProgram list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<List<CategoryProgram>>> getAllCategoryProgram(
    ) {

        CommonResponse<List<CategoryProgram>> response = new CommonResponse<>();
        try {
            List<CategoryProgram> beritaPage = categoryprogramService.getAllNoPage();
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" CategoryProgram list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Page<CategoryProgram>>> listAllCategoryProgramTerbaru(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<CategoryProgram>> response = new CommonResponse<>();
        try {
            Page<CategoryProgram> beritaPage = categoryprogramService.getAllTerbaru(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" CategoryProgram list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<CategoryProgram>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<CategoryProgram> response = new CommonResponse<>();
        try {
            CategoryProgram categoryBerita = categoryprogramService.getById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("CategoryProgram get successfully.");
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
    public ResponseEntity<CommonResponse<CategoryProgram>> updateCategoryProgram(@PathVariable("id") Long id, @RequestBody CategoryProgram categoryprogram) throws SQLException, ClassNotFoundException {
        CommonResponse<CategoryProgram> response = new CommonResponse<>();
        try {
            CategoryProgram tabelDip = categoryprogramService.edit(categoryprogram, id);
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
        return ResponseEntity.ok(categoryprogramService.delete(id));
    }
}
