package com.smpn1.bergas.model;

import com.smpn1.bergas.auditing.DateConfig;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "prestasi")
public class Prestasi extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "judul")
    private String judul;

    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "Asia/Jakarta")
    @Column(name = "tanggal")
    private Date tanggal;

    @Column(name = "skala")
    private String skala;

    @Column(name = "nama_peserta")
    private String nama_peserta;

    @Column(name = "peyelenggara")
    private String peyelenggara;

    @Lob
    @Column(name = "foto")
    private String foto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getSkala() {
        return skala;
    }

    public void setSkala(String skala) {
        this.skala = skala;
    }

    public String getNama_peserta() {
        return nama_peserta;
    }

    public void setNama_peserta(String nama_peserta) {
        this.nama_peserta = nama_peserta;
    }

    public String getPeyelenggara() {
        return peyelenggara;
    }

    public void setPeyelenggara(String peyelenggara) {
        this.peyelenggara = peyelenggara;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
