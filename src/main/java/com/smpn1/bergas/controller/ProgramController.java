package com.smpn1.bergas.controller;


import com.smpn1.bergas.DTO.ProgramDTO;
import com.smpn1.bergas.model.Program;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.ProgramService;
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
@RequestMapping("/smpn1bergas/api/program")
@CrossOrigin(origins = "*")
public class ProgramController {
    @Autowired
    private ProgramService programService;

    @PostMapping(path = "/add")
    public ResponseEntity<CommonResponse<Program>> add(@RequestBody ProgramDTO program) throws SQLException, ClassNotFoundException {
        CommonResponse<Program> response = new CommonResponse<>();
        try {
            Program program1 = programService.add(program);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(program1);
            response.setMessage("Program created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create program: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse<Page<Program>>> listAllProgram(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Program>> response = new CommonResponse<>();
        try {
            Page<Program> beritaPage = programService.getAll(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Program list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve guru list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/all/category")
    public ResponseEntity<CommonResponse<Page<Program>>> listAllProgramByCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(name = "id_category") Long id
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Program>> response = new CommonResponse<>();
        try {
            Page<Program> beritaPage = programService.getAllByKategory(id,pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Program list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Page<Program>>> listAllProgramTerbaru(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Program>> response = new CommonResponse<>();
        try {
            Page<Program> beritaPage = programService.getAllTerbaru(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Program list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Program>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<Program> response = new CommonResponse<>();
        try {
            Program categoryBerita = programService.getById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("Program get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get program: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(path = "/get/judul")
    public ResponseEntity<CommonResponse<Page<Program>>> getByJudul(
            @RequestParam(name = "judul_program") String judul,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Program>> response = new CommonResponse<>();
        try {
            Page<Program> beritaPage = programService.getByJudul(judul, pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Program list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve keuangan list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(path = "/put/{id}", produces = "application/json")
    public ResponseEntity<CommonResponse<Program>> updateProgram(@PathVariable("id") Long id, @RequestBody ProgramDTO program) throws SQLException, ClassNotFoundException {
        CommonResponse<Program> response = new CommonResponse<>();
        try {
            Program tabelDip = programService.edit(program, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage("Program updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update program : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(programService.delete(id));
    }
}
