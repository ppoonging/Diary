package com.diary.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "site_user")
public class SiteUser {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true)
    private String username;//아이디

    private String password;

    @Column(unique = true)
    private String myName;  // 닉네임 이걸로 네이버 name 받아보자

    @Column(unique = true)
    @Email
    private String email;

    private String address;

    @Column(unique = true)
    private String phoneNumber;


    private String provider;  // 소셜 로그인 플랫폼: naver, google

    // 네이버 사용자 생성용 생성자
    public SiteUser(String username, String myName, String email, String provider) {
        this.username = username;
        this.myName = myName;
        this.email = email;
        this.provider = provider;
    }

    // 기본 생성자 (JPA용)
    public SiteUser() {}

    }

