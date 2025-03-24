package com.diary.user;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true)
    private String username;//아이디

    private String password;

    @Column(unique = true)
    private String myName;  // 닉네임
    @Column(unique = true)
    private String email;


    @Column(unique = true)
    private String phoneNumber;

}
