package com.smpn1.bergas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // API controller
            "/login", "/register",
            "/api/alumni/all/**",
            "/api/alumni/get/**",
            "/api/berita/all/**",
            "/api/berita/get/**",
            "/api/berita/terbaru",
            "/api/berita/search",
            "/api/berita/arsip",
            "/api/berita/by-category",
            "/api/berita/related-berita/by-id-berita",
            "/api/berita/terbaru-by-category",
            "/api/ekstrakulikuler/all/**",
            "/api/ekstrakulikuler/get/**",
            "/api/foto_kegiatan/all/**",
            "/api/foto_kegiatan/get/**",
            "/api/foto_sarana/all/**",
            "/api/foto_sarana/get/**",
            "/api/galeri/all/**",
            "/api/galeri/get/**",
            "/api/guru/all/**",
            "/api/guru/get/**",
            "/api/kegiatan/all/**",
            "/api/kegiatan/get/**",
            "/api/keuangan/all/**",
            "/api/keuangan/get/**",
            "/api/keuangan/category",
            "/api/kontak/all/**",
            "/api/kontak/get/**",
            "/api/prestasi/all/**",
            "/api/prestasi/get/**",
            "/api/program/all/**",
            "/api/program/get/**",
            "/api/sambutan/all/**",
            "/api/sambutan/get/**",
            "/api/sarana/all/**",
            "/api/sarana/get/**",
            "/api/sejarah/all/**",
            "/api/sejarah/get/**",
            "/api/struktur/all/**",
            "/api/struktur/get/**",
            "/api/tenaga_kependidikan/all/**",
            "/api/tenaga_kependidikan/get/**",
            "/api/visiMisi/all/**",
            "/api/visiMisi/get/**",
            "/api/kotak_saran/add",
            "/api/perpustakaan/get/**",
            "/api/perpustakaan/all/**",
            "/api/materi_ajar/get/**",
            "/api/materi_ajar/all/**",
            "/api/kondisi_sekolah/all/**",
            "/api/kondisi_sekolah/get/**",
            "/api/osis/all/**",
            "/api/osis/get/**",
            "/api/category_program/all/**",
            "/api/category_program/get/**",
            "/api/category_program/all/terbaru/**",
            "/api/program/all/terbaru/**",
    };

    private static final String[] AUTH_AUTHORIZATION = {
            "/bawaslu/api/berita/**",
            "/bawaslu/api/pengumuman/**",
            "/bawaslu/api/isi-keterangan-informasi/**",
            "/bawaslu/api/jenis-informasi/**",
            "/bawaslu/api/jenis-keterangan/**",
            "/bawaslu/api/permohonan-informasi/**",
            "/bawaslu/api/permohonan-keberatan/**",
            "/bawaslu/api/tags/**",
            "/bawaslu/api/jenis-regulasi/**",
            "/bawaslu/api/menu-regulasi/**",
            "/bawaslu/api/regulasi/**",
            "/bawaslu/api/category-berita/**",
            "/bawaslu/api/tabel-regulasi/**",
            "/bawaslu/api/tabel-dip/**",
            "/bawaslu/api/tabel-sop/**",
            "/bawaslu/api/carousel/**",
            "/bawaslu/api/library/**",
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(AUTH_AUTHORIZATION).hasRole("ADMIN")
                .antMatchers(AUTH_AUTHORIZATION).hasAnyRole( "ADMIN")
                .anyRequest()
                .authenticated().and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}