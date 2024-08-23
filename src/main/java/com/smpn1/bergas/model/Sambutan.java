package com.smpn1.bergas.model;

import com.smpn1.bergas.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "sambutan")
public class Sambutan extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama")
    private String nama;

    @Lob
    @Column(name = "isi_sambutan")
    private String isi;

    @Column(name = "judul")
    private String judul;


    @Column(name = "NIP")
    private String nip;
    @Lob
    @Column(name = "foto")
    private String foto;

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }
}
