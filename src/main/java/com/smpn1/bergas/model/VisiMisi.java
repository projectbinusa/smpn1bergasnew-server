package com.smpn1.bergas.model;

import com.smpn1.bergas.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "visi_misi")
public class VisiMisi extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "visi")
    private String visi;

    @Lob
    @Column(name = "misi")
    private String misi;

    @Lob
    @Column(name = "tujuan")
    private String tujuan;

    @Lob
    @Column(name = "analisis_lingkungan_internal")
    private String analisis_lingkungan_internal;

    @Lob
    @Column(name = "sasaran_sekolah")
    private String sasaran_sekolah;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVisi() {
        return visi;
    }

    public void setVisi(String visi) {
        this.visi = visi;
    }

    public String getMisi() {
        return misi;
    }

    public void setMisi(String misi) {
        this.misi = misi;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getAnalisis_lingkungan_internal() {
        return analisis_lingkungan_internal;
    }

    public void setAnalisis_lingkungan_internal(String analisis_lingkungan_sekolah) {
        this.analisis_lingkungan_internal = analisis_lingkungan_sekolah;
    }

    public String getSasaran_sekolah() {
        return sasaran_sekolah;
    }

    public void setSasaran_sekolah(String sasaran_sekolah) {
        this.sasaran_sekolah = sasaran_sekolah;
    }
}
