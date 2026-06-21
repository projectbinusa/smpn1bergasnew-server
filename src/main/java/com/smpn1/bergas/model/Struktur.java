package com.smpn1.bergas.model;

import com.smpn1.bergas.auditing.DateConfig;



import javax.persistence.*;

@Entity
@Table(name = "struktur")
public class Struktur extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_anggota")
    private String nama;

    @Column(name = "jabatan")
    private String jabatan;

    @Column(name = "tugas_anggota")
    private String tugas;

    @Lob
    @Column(name = "foto_anggota")
    private String foto;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;



    // @Column(name = "jenis")
    // private String jenisStruktur;

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

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getTugas() {
        return tugas;
    }

    public void setTugas(String tugas) {
        this.tugas = tugas;
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

    // public String getJenisStruktur() {
    // return jenisStruktur;
    // }
    //
    // public void setJenisStruktur(String jenisStruktur) {
    // this.jenisStruktur = jenisStruktur;
    // }
}
