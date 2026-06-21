package com.smpn1.bergas.model;

import com.smpn1.bergas.auditing.DateConfig;



import javax.persistence.*;

@Entity
@Table(name = "sejarah")
public class Sejarah extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "judul")
    private String judul;

    @Lob
    @Column(name = "isi")
    private String isi;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;



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

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
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
