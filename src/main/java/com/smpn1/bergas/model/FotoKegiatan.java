package com.smpn1.bergas.model;

import com.smpn1.bergas.auditing.DateConfig;



import javax.persistence.*;

@Entity
@Table(name = "foto_kegiatan")
public class FotoKegiatan extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kegiatan_id")
    private Kegiatan kegiatan;

    @Lob
    @Column(name = "foto")
    private String foto;

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

    public Kegiatan getKegiatan() {
        return kegiatan;
    }

    public void setKegiatan(Kegiatan kegiatan) {
        this.kegiatan = kegiatan;
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
