package com.smpn1.bergas.model;

import com.smpn1.bergas.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "osis")
public class Osis extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama")
    private String nama;

    @Column(name = "kelas")
    private String kelas;

    @Column(name = "jabatan")
    private String jabatan;

    @Column(name = "tahun_jabat")
    private String tahunJabat;

    @Column(name = "tahun_tuntas")
    private String tahunTuntas;

    @Lob
    @Column(name = "foto")
    private String foto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getTahunJabat() {
        return tahunJabat;
    }

    public void setTahunJabat(String tahunJabat) {
        this.tahunJabat = tahunJabat;
    }

    public String getTahunTuntas() {
        return tahunTuntas;
    }

    public void setTahunTuntas(String tahunTuntas) {
        this.tahunTuntas = tahunTuntas;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
