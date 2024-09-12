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
            "/smpn1bergas/api/alumni/all/**",
            "/smpn1bergas/api/alumni/get/**",
            "/smpn1bergas/api/berita/all/**",
            "/smpn1bergas/api/berita/get/**",
            "/smpn1bergas/api/berita/terbaru",
            "/smpn1bergas/api/berita/search",
            "/smpn1bergas/api/berita/arsip",
            "/smpn1bergas/api/berita/by-category",
            "/smpn1bergas/api/berita/related-berita/by-id-berita",
            "/smpn1bergas/api/berita/terbaru-by-category",
            "/smpn1bergas/api/ekstrakulikuler/all/**",
            "/smpn1bergas/api/ekstrakulikuler/get/**",
            "/smpn1bergas/api/foto_kegiatan/all/**",
            "/smpn1bergas/api/foto_kegiatan/get/**",
            "/smpn1bergas/api/foto_sarana/all/**",
            "/smpn1bergas/api/foto_sarana/get/**",
            "/smpn1bergas/api/galeri/all/**",
            "/smpn1bergas/api/galeri/get/**",
            "/smpn1bergas/api/guru/all/**",
            "/smpn1bergas/api/guru/get/**",
            "/smpn1bergas/api/kegiatan/all/**",
            "/smpn1bergas/api/kegiatan/get/**",
            "/smpn1bergas/api/keuangan/all/**",
            "/smpn1bergas/api/keuangan/get/**",
            "/smpn1bergas/api/keuangan/category",
            "/smpn1bergas/api/kontak/all/**",
            "/smpn1bergas/api/kontak/get/**",
            "/smpn1bergas/api/prestasi/all/**",
            "/smpn1bergas/api/prestasi/get/**",
            "/smpn1bergas/api/program/all/**",
            "/smpn1bergas/api/program/get/**",
            "/smpn1bergas/api/sambutan/all/**",
            "/smpn1bergas/api/sambutan/get/**",
            "/smpn1bergas/api/sarana/all/**",
            "/smpn1bergas/api/sarana/get/**",
            "/smpn1bergas/api/sejarah/all/**",
            "/smpn1bergas/api/sejarah/get/**",
            "/smpn1bergas/api/struktur/all/**",
            "/smpn1bergas/api/struktur/get/**",
            "/smpn1bergas/api/tenaga_kependidikan/all/**",
            "/smpn1bergas/api/tenaga_kependidikan/get/**",
            "/smpn1bergas/api/visiMisi/all/**",
            "/smpn1bergas/api/visiMisi/get/**",
            "/smpn1bergas/api/kotak_saran/add",
            "/smpn1bergas/api/perpustakaan/get/**",
            "/smpn1bergas/api/perpustakaan/all/**",
            "/smpn1bergas/api/materi_ajar/get/**",
            "/smpn1bergas/api/materi_ajar/all/**",
            "/smpn1bergas/api/kondisi_sekolah/all/**",
            "/smpn1bergas/api/kondisi_sekolah/get/**",
            "/smpn1bergas/api/osis/all/**",
            "/smpn1bergas/api/osis/get/**",
            "/smpn1bergas/api/category_program/all/**",
            "/smpn1bergas/api/category_program/get/**",
            "/smpn1bergas/api/category_program/all/terbaru/**",
            "/smpn1bergas/api/program/all/terbaru/**",
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