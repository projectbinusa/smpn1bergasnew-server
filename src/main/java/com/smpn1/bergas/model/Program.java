package com.smpn1.bergas.model;
import com.smpn1.bergas.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "program")
public class Program extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama_program")
    private String namaProgram;

//    @Column(name = "judul_program")
//    private String judulProgram;

    @Column(name = "tujuan")
    private String tujuan;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private CategoryProgram categoryProgram;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaProgram() {
        return namaProgram;
    }

    public void setNamaProgram(String namaProgram) {
        this.namaProgram = namaProgram;
    }

//    public String getJudulProgram() {
//        return judulProgram;
//    }
//
//    public void setJudulProgram(String judulProgram) {
//        this.judulProgram = judulProgram;
//    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public CategoryProgram getCategoryProgram() {
        return categoryProgram;
    }

    public void setCategoryProgram(CategoryProgram categoryProgram) {
        this.categoryProgram = categoryProgram;
    }
}
