package com.smpn1.bergas.model;

import com.smpn1.bergas.auditing.DateConfig;



import javax.persistence.*;

@Entity
@Table(name = "jenjang")

public class Jenjang extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_jenjang")
    private String nama_jenjang;

    @Column(name = "link")
    private String link;

    @Lob
    @Column(name = "description")
    private String description;

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

    public String getNama_jenjang() {
        return nama_jenjang;
    }

    public void setNama_jenjang(String nama_jenjang) {
        this.nama_jenjang = nama_jenjang;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
