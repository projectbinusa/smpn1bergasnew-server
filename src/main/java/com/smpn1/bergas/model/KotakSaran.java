package com.smpn1.bergas.model;

import com.smpn1.bergas.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "kotak_saran")
public class KotakSaran extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama")
    private String nama;
    @Column(name = "email")
    private String email;

    @Column(name = "no_telp")
    private String telp;

    @Column(name = "pesan")
    private String pesan;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}
