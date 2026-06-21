package com.smpn1.bergas.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.smpn1.bergas.auditing.DateConfig;


import com.smpn1.bergas.util.StringArrayJsonConverter;

@Entity
@Table(name = "laporan_bosp")
public class LaporanBosp extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "nama")
    private String nama;
    @Lob
    @Column(name = "deskripsi")
    private String deskripsi;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;



    @Lob
    @Column(name = "files")
    @Convert(converter = StringArrayJsonConverter.class)
    private String[] files;

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

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String[] getFiles() {
        return files;
    }

    public void setFiles(String[] files) {
        this.files = files;
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
