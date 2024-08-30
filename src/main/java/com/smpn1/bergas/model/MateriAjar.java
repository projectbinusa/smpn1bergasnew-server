package com.smpn1.bergas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smpn1.bergas.auditing.DateConfig;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "materi_ajar")
public class MateriAjar extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tingkat")
    private String tingkat;

    @Column(name = "mapel")
    private String mapel;

    @Column(name = "judul")
    private String judul;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Jakarta")
//    @Column(name = "tgl_upload")
//    private Date tglUpload;

    @Column(name = "jenis")
    private String jenis;

    @Column(name = "penyusun")
    private String penyusun;

    @Lob
    @Column(name = "isi_download")
    private String isi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTingkat() {
        return tingkat;
    }

    public void setTingkat(String tingkat) {
        this.tingkat = tingkat;
    }

    public String getMapel() {
        return mapel;
    }

    public void setMapel(String mapel) {
        this.mapel = mapel;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

//    public Date getTglUpload() {
//        return tglUpload;
//    }
//
//    public void setTglUpload(Date tglUpload) {
//        this.tglUpload = tglUpload;
//    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getPenyusun() {
        return penyusun;
    }

    public void setPenyusun(String penyusun) {
        this.penyusun = penyusun;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }
}
