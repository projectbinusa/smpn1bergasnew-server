package com.smpn1.bergas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.smpn1.bergas.auditing.DateConfig;



@Entity
@Table(name = "guru")
public class Guru extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_guru")
    private String nama_guru;

    @Column(name = "mapel")
    private String mapel;
    @Lob
    @Column(name = "foto")
    private String foto;

    @Column(name = "nip")
    private String nip;

    @Column(name = "riwayat_pendidikan")
    private String riwayat;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;



    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getRiwayat() {
        return riwayat;
    }

    public void setRiwayat(String riwayat) {
        this.riwayat = riwayat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama_guru() {
        return nama_guru;
    }

    public void setNama_guru(String nama_guru) {
        this.nama_guru = nama_guru;
    }

    public String getMapel() {
        return mapel;
    }

    public void setMapel(String mapel) {
        this.mapel = mapel;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

   public void setUserName(String userName) {
        this.userName = userName;
    }
}
