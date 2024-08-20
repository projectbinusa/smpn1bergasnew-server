package com.smpn1.bergas.model;

import com.smpn1.bergas.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "keuangan")
public class Keuangan extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "judul_keuangan")
    private String judul;

    @Lob
    @Column(name = "foto_judul")
    private String fotoJudul;

    @Lob
    @Column(name = "isi")
    private String isi;


    @Column(name = "category")
    private String categoryKeuangan;

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

    public String getFotoJudul() {
        return fotoJudul;
    }

    public void setFotoJudul(String fotoJudul) {
        this.fotoJudul = fotoJudul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getCategoryKeuangan() {
        return categoryKeuangan;
    }

    public void setCategoryKeuangan(String categoryKeuangan) {
        this.categoryKeuangan = categoryKeuangan;
    }
}
