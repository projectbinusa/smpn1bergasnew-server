package com.smpn1.bergas.controller;

import com.smpn1.bergas.DTO.LaporanBospDTO;
import com.smpn1.bergas.model.LaporanBosp;
import com.smpn1.bergas.response.CommonResponse;
import com.smpn1.bergas.service.LaporanBospService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/laporanbosp")
@CrossOrigin(origins = "*")
public class LaporanBospController {

    @Autowired
    private LaporanBospService laporanService;

    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<LaporanBosp>> createLaporan(
            @RequestPart("laporan") LaporanBospDTO laporan,
            @RequestPart(value = "files", required = false) MultipartFile[] multipartFiles) {
        CommonResponse<LaporanBosp> response = new CommonResponse<>();
        try {
            LaporanBosp saved = laporanService.save(laporan, multipartFiles);
            response.setStatus("success");
            response.setCode(HttpStatus.CREATED.value());
            response.setData(saved);
            response.setMessage("Laporan created successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse<Page<LaporanBosp>>> listAllLaporanBosp(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        Pageable pageable;
        if (sortOrder.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        CommonResponse<Page<LaporanBosp>> response = new CommonResponse<>();
        try {
            Page<LaporanBosp> laporanPage = laporanService.findAllWithPagination(pageable);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(laporanPage);
            response.setMessage("Laporan list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve berita list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/put/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommonResponse<LaporanBosp>> updateLaporan(
            @PathVariable Long id,
            @RequestPart("laporan") LaporanBospDTO laporan,
            @RequestPart(value = "files", required = false) MultipartFile[] multipartFiles) {
        CommonResponse<LaporanBosp> response = new CommonResponse<>();
        try {
            LaporanBosp updated = laporanService.update(id, laporan, multipartFiles);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(updated);
            response.setMessage("Laporan updated successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PutMapping(path = "/put/foto/{id}", consumes = "multipart/form-data")
    // public ResponseEntity<CommonResponse<Berita>> updateFoto(@PathVariable("id") Long id, @RequestPart("file") MultipartFile multipartFile) throws SQLException, ClassNotFoundException {
    //     CommonResponse<Berita> response = new CommonResponse<>();
    //     try {
    //         Optional<Berita> currentBerita = laporanService.findById(id);

    //         if (!currentBerita.isPresent()) {
    //             response.setStatus("error");
    //             response.setCode(HttpStatus.NOT_FOUND.value());
    //             response.setData(null);
    //             response.setMessage("Berita with id " + id + " not found.");
    //             return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    //         }

    //         // Update berita here...

    //         Berita berita1 = laporanService.updateFoto(id, multipartFile);
    //         response.setStatus("success");
    //         response.setCode(HttpStatus.OK.value());
    //         response.setData(berita1);
    //         response.setMessage("Berita updated successfully.");
    //         return new ResponseEntity<>(response, HttpStatus.OK);
    //     } catch (Exception e) {
    //         response.setStatus("error");
    //         response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    //         response.setData(null);
    //         response.setMessage("Failed to update berita: " + e.getMessage());
    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<CommonResponse<String>> deleteLaporan(@PathVariable("id") Long id) throws SQLException, ClassNotFoundException {
        CommonResponse<String> response = new CommonResponse<>();
        try {
            laporanService.delete(id);
            response.setStatus("success");
            response.setCode(HttpStatus.NO_CONTENT.value());
            response.setData("Laporan deleted successfully.");
            response.setMessage("Laporan with id " + id + " deleted successfully.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to delete laporan: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping(path = "/terbaru")
    // public ResponseEntity<CommonResponse<List<Berita>>> listBeritaTerbaru() throws SQLException, ClassNotFoundException {
    //     CommonResponse<List<Berita>> response = new CommonResponse<>();
    //     try {
    //         List<Berita> berita = laporanService.beritaTerbaru();
    //         response.setStatus("success");
    //         response.setCode(HttpStatus.OK.value());
    //         response.setData(berita);
    //         response.setMessage("Berita list retrieved successfully.");
    //         return new ResponseEntity<>(response, HttpStatus.OK);
    //     } catch (Exception e) {
    //         response.setStatus("error");
    //         response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    //         response.setData(null);
    //         response.setMessage("Failed to retrieve berita list: " + e.getMessage());
    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    @GetMapping(path = "/search")
    public ResponseEntity<CommonResponse<List<LaporanBosp>>> searchLaporanBosp(@RequestParam("search") String nama) {
        CommonResponse<List<LaporanBosp>> response = new CommonResponse<>();
        try {
            List<LaporanBosp> laporans = laporanService.searchLaporanBosp(nama);
            if(laporans.isEmpty()) {
                response.setStatus("not found");
                response.setCode(HttpStatus.NOT_FOUND.value());
                response.setData(null);
                response.setMessage("Laporan list not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(laporans);
            response.setMessage("Laporan list retrieved successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to retrieve berita list: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping(path = "/arsip")
    // public ResponseEntity<CommonResponse<List<Berita>>> listBeritaArsip(@RequestParam("bulan") String bulan) throws SQLException, ClassNotFoundException {
    //     CommonResponse<List<Berita>> response = new CommonResponse<>();
    //     try {
    //         List<Berita> berita = laporanService.arsip(bulan);
    //         if(berita.isEmpty()) {
    //             response.setStatus("not found");
    //             response.setCode(HttpStatus.NOT_FOUND.value());
    //             response.setData(null);
    //             response.setMessage("Berita list not found");
    //             return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    //         }
    //         response.setStatus("success");
    //         response.setCode(HttpStatus.OK.value());
    //         response.setData(berita);
    //         response.setMessage("Berita list retrieved successfully.");
    //         return new ResponseEntity<>(response, HttpStatus.OK);
    //     } catch (Exception e) {
    //         response.setStatus("error");
    //         response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    //         response.setData(null);
    //         response.setMessage("Failed to retrieve berita list: " + e.getMessage());
    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<CommonResponse<LaporanBosp>> get(@PathVariable("id") long id) throws SQLException, ClassNotFoundException {
        CommonResponse<LaporanBosp> response = new CommonResponse<>();
        try {
            LaporanBosp berita1 = laporanService.getLaporanBospById(id);
            response.setStatus("success");
            response.setCode(HttpStatus.OK.value());
            response.setData(berita1);
            response.setMessage("Berita get successfully.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus("error");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);
            response.setMessage("Failed to get berita: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @GetMapping("/by-category")
    // public ResponseEntity<CommonResponse<Page<Berita>>> allByCategory(
    //         @RequestParam("category") String category,
    //         @RequestParam(value = "page", defaultValue = "0") int page,
    //         @RequestParam(value = "size", defaultValue = "10") int size,
    //         @RequestParam(value = "sort", defaultValue = "created_date") String sort,
    //         @RequestParam(value = "order", defaultValue = "asc") String order) {
    //     try {
    //         Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort));
    //         Page<Berita> beritas = laporanService.getByCategory(category, pageable);

    //         CommonResponse<Page<Berita>> response = new CommonResponse<>();
    //         response.setStatus("success");
    //         response.setCode(HttpStatus.OK.value());
    //         response.setData(beritas);
    //         response.setMessage("Berita list retrieved successfully.");

    //         return new ResponseEntity<>(response, HttpStatus.OK);
    //     } catch (Exception e) {
    //         CommonResponse<Page<Berita>> response = new CommonResponse<>();
    //         response.setStatus("error");
    //         response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    //         response.setData(null);
    //         response.setMessage("Failed to retrieve berita list: " + e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    // @GetMapping(path = "/related-berita/by-id-berita")
    // public ResponseEntity<CommonResponse<List<Berita>>> relatedPosts(@RequestParam("id") Long id) {
    //     CommonResponse<List<Berita>> response = new CommonResponse<>();
    //     try {
    //         List<Berita> beritas = laporanService.relatedPosts(id);
    //         response.setStatus("success");
    //         response.setCode(HttpStatus.OK.value());
    //         response.setData(beritas);
    //         response.setMessage("Berita list retrieved successfully.");
    //         return new ResponseEntity<>(response, HttpStatus.OK);
    //     } catch (Exception e) {
    //         response.setStatus("error");
    //         response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    //         response.setData(null);
    //         response.setMessage("Failed to retrieve berita list: " + e.getMessage());
    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    // @GetMapping(path = "/terbaru-by-category")
    // public ResponseEntity<CommonResponse<List<Berita>>> beritaTerbaruByCategory(@RequestParam("categoryId") Long categoryId) {
    //     CommonResponse<List<Berita>> response = new CommonResponse<>();
    //     try {
    //         List<Berita> beritas = laporanService.terbaruByCategory(categoryId);
    //         response.setStatus("success");
    //         response.setCode(HttpStatus.OK.value());
    //         response.setData(beritas);
    //         response.setMessage("Berita list retrieved successfully.");
    //         return new ResponseEntity<>(response, HttpStatus.OK);
    //     } catch (Exception e) {
    //         response.setStatus("error");
    //         response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    //         response.setData(null);
    //         response.setMessage("Failed to retrieve berita list: " + e.getMessage());
    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

}
