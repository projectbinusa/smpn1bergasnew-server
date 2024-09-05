package com.smpn1.bergas.model;

import com.smpn1.bergas.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "alumni")
public class Alumni extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "nama_alumni")
    private String nama;
    @Lob
    @Column(name = "bigrafi")
    private String biografi;

    @Lob
    @Column(name = "foto")
    private String foto;
    @Column(name = "tahun_lulus")
    private String tahunLulus;

    @Column(name = "profesi")
    private String profesi;

    @Column(name = "kontak")
    private String kontak;

//    @Column(name = "nip")
//    private String nip;
//
//    @Column(name = "riwayat_pendidikan")
//    private String riwayat;

//    public String getNip() {
//        return nip;
//    }
//
//    public void setNip(String nip) {
//        this.nip = nip;
//    }
//
//    public String getRiwayat() {
//        return riwayat;
//    }
//
//    public void setRiwayat(String riwayat) {
//        this.riwayat = riwayat;
//    }

    public String getTahunLulus() {
        return tahunLulus;
    }

    public void setTahunLulus(String tahunLulus) {
        this.tahunLulus = tahunLulus;
    }

    public String getProfesi() {
        return profesi;
    }

    public void setProfesi(String profesi) {
        this.profesi = profesi;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

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

    public String getBiografi() {
        return biografi;
    }

    public void setBiografi(String biografi) {
        this.biografi = biografi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
