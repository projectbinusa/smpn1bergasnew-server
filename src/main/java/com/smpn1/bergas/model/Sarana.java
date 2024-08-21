package com.smpn1.bergas.model;

import com.smpn1.bergas.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "sarana")
public class Sarana extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_sarana")
    private String nama_sarana;

    @Column(name = "deskripsi")
    private String deskripsi;

    @Column(name = "category")
    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama_sarana() {
        return nama_sarana;
    }

    public void setNama_sarana(String nama_sarana) {
        this.nama_sarana = nama_sarana;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
