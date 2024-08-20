package com.smpn1.bergas.service;

import com.smpn1.bergas.DTO.BeritaDTO;
import com.smpn1.bergas.model.Berita;
import com.smpn1.bergas.repository.BeritaRepository;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;


@Service
public class BeritaService {

    @Autowired
    private BeritaRepository beritaDao;




    private long id;

    public BeritaService() {
    }

    private static final String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/bawaslu-a6bd2.appspot.com/o/%s?alt=media";

    public Berita save(BeritaDTO berita, MultipartFile multipartFile) throws Exception {
        Berita newBerita = new Berita();
        String image = imageConverter(multipartFile);
        newBerita.setAuthor(berita.getAuthor());
        newBerita.setJudulBerita(berita.getJudulBerita());
        newBerita.setIsiBerita(berita.getIsiBerita());
        newBerita.setImage(image);
        newBerita.setCategoryBerita(berita.getCategory());

        return beritaDao.save(newBerita);
    }

    public Optional<Berita> findById(Long id) {
        return Optional.ofNullable(beritaDao.findById(id));
    }

    public Page<Berita> findAllWithPagination(Pageable pageable) {
        return beritaDao.findAllByOrderByUpdatedDateDesc(pageable);
    }

    @Transactional
    public void delete(Long id) {
        Berita berita = beritaDao.findById(id);

        if (berita != null) {
            berita.setCategoryBerita(null);

            beritaDao.delete(berita);
        } else {
            System.out.println("Entity with id " + id + " not found.");
        }
    }

    public Berita update(Long id, BeritaDTO beritaDTO, MultipartFile multipartFile) throws Exception {
        Berita berita = beritaDao.findById(id);
        String image = imageConverter(multipartFile);
        berita.setJudulBerita(beritaDTO.getJudulBerita());
        berita.setIsiBerita(beritaDTO.getIsiBerita());
        berita.setAuthor(beritaDTO.getAuthor());
        berita.setImage(image);
        berita.setCategoryBerita(beritaDTO.getCategory());
        return beritaDao.save(berita);
    }


    public List<Berita> beritaTerbaru(){
        return beritaDao.findFirst5ByOrderByUpdatedDateDesc();
    }

    public List<Berita> searchBerita(String judul) {
        return beritaDao.searchByJudulBerita(judul);
    }

    public Berita getBeritaById(Long id) throws Exception {
        Berita berita = beritaDao.findById(id);
        if (berita == null) throw new Exception("Berita not found!!!");
        return berita;
    }

    public List<Berita> arsip(String bulan){
        return beritaDao.find(bulan);
    }


    public List<Berita> getByTags(Long tagsId) {
        return beritaDao.getAllByTags(tagsId);
    }

    public Page<Berita> getByCategory(String categoryId, Pageable pageable) {
        return beritaDao.findByCategoryBerita_Id(categoryId, pageable);
    }
    public List<Berita> relatedPosts(Long idBerita) throws Exception {
        String berita = beritaDao.getByIdBerita(idBerita);
        return beritaDao.relatedPost(berita);
    }

    public List<Berita> terbaruByCategory(Long categoryId) {
        return beritaDao.terbaruByCategory(categoryId);
    }

    private String imageConverter(MultipartFile multipartFile) throws Exception {
        try {
            String fileName = getExtension(multipartFile.getOriginalFilename());
            File file = convertFile(multipartFile, fileName);
            var RESPONSE_URL = uploadFile(file, fileName);
            file.delete();
            return RESPONSE_URL;
        } catch (Exception e) {
            e.getStackTrace();
            throw new Exception("Error upload file: " + e.getMessage());
        }
    }

    private String getExtension(String fileName) {
        return  fileName.split("\\.")[0];
    }

    private File convertFile(MultipartFile multipartFile, String fileName) throws IOException {
        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        System.out.println("File size: " + file.length());
        return file;
    }

    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("bawaslu-a6bd2.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("bawaslu-firebase.json");
        Credentials credentials = GoogleCredentials.fromStream(serviceAccount);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

}