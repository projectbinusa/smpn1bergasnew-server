package com.smpn1.bergas.model;



import com.smpn1.bergas.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "berita")
public class Berita extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String judulBerita;
    private String author;
    @Lob
    private String isiBerita;
    @Lob
    private String image;

    @Column(name = "category")
    private String categoryBerita;

    public Berita() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJudulBerita() {
        return judulBerita;
    }

    public void setJudulBerita(String judulBerita) {
        this.judulBerita = judulBerita;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsiBerita() {
        return isiBerita;
    }

    public void setIsiBerita(String isiBerita) {
        this.isiBerita = isiBerita;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getCategoryBerita() {
        return categoryBerita;
    }

    public void setCategoryBerita(String categoryBerita) {
        this.categoryBerita = categoryBerita;
    }
}
