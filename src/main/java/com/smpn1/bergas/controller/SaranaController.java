package com.smpn1.bergas.controller;

import com.smpn1.bergas.config.JwtTokenUtil;
import com.smpn1.bergas.model.Berita;
import com.smpn1.bergas.model.Sarana;
import com.smpn1.bergas.model.Sarana;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.SaranaService;
import com.smpn1.bergas.util.DomainUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/api/sarana")
@CrossOrigin(origins = "*")
public class SaranaController {
    @Autowired
    private SaranaService saranaService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private DomainUtil domainUtil;

    @PostMapping(path = "/add")
    public ResponseEntity<CommonResponse<Sarana>> add(@RequestBody Sarana sarana)
            throws SQLException, ClassNotFoundException {
        CommonResponse<Sarana> response = new CommonResponse<>();
        try {
            Sarana sarana1 = saranaService.add(sarana);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(sarana1);
            response.setMessage("Sarana created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to create sarana: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse<Page<Sarana>>> listAllSarana(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Sarana>> response = new CommonResponse<>();
        try {
            Page<Sarana> beritaPage = saranaService.getAll(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Sarana list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve sarana list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/admin/all")
    public ResponseEntity<CommonResponse<Page<Sarana>>> listAllSarana(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        String token = request.getHeader("Authorization").substring(7);
        Long userId = jwtTokenUtil.getUserIdFromToken(token);

        Pageable pageable;
        if (sortOrder.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        CommonResponse<Page<Sarana>> response = new CommonResponse<>();

        try {
            Page<Sarana> saranaPage = saranaService.findAllWithPaginationByUserId(
                    userId,
                    pageable);

            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(saranaPage);
            response.setMessage("Sarana list retrieved successfully.");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve sarana list: " + e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all/terbaru")
    public ResponseEntity<CommonResponse<Page<Sarana>>> listAllSaranaTerbaru(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);

        CommonResponse<Page<Sarana>> response = new CommonResponse<>();
        try {
            Page<Sarana> beritaPage = saranaService.getAllTerbaru(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(beritaPage);
            response.setMessage(" Sarana list retrieved successfully.");
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
        public ResponseEntity<CommonResponse<Page<Sarana>>> listAllSaranaCategory(
                HttpServletRequest request,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "20") int size,
                @RequestParam("category") String category,
                @RequestParam(defaultValue = "createdDate") String sortBy,
                @RequestParam(defaultValue = "desc") String sortOrder) {

            Pageable pageable;
            if (sortOrder.equals("asc")) {
                pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
            } else {
                pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
            }
            CommonResponse<Page<Sarana>> response = new CommonResponse<>();
            try {
                Long userId = domainUtil.getCurrentUserId(request);

                Page<Sarana> beritaPage = saranaService.getAllCategory(userId, category, pageable);
                response.setStatus("success");
                response.setCode(HttpStatus.OK.value());
                response.setData(beritaPage);
                response.setMessage(" Sarana list retrieved successfully.");
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
    public ResponseEntity<CommonResponse<Sarana>> get(@PathVariable("id") long id)
            throws SQLException, ClassNotFoundException {
        CommonResponse<Sarana> response = new CommonResponse<>();
        try {
            Sarana categoryBerita = saranaService.getById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(categoryBerita);
            response.setMessage("Sarana get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get sarana: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/put/{id}", produces = "application/json")
    public ResponseEntity<CommonResponse<Sarana>> updateSarana(@PathVariable("id") Long id, @RequestBody Sarana sarana)
            throws SQLException, ClassNotFoundException {
        CommonResponse<Sarana> response = new CommonResponse<>();
        try {
            Sarana tabelDip = saranaService.edit(sarana, id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(tabelDip);
            response.setMessage(" Sarana updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to update sarana : " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(saranaService.delete(id));
    }
}
