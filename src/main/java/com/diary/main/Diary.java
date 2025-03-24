package com.diary.main;

import com.diary.user.SiteUser;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 30)
    private String username;
    @Column(length = 30)
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String content;

    private String weather;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime createDateTime; /*작성일*/

    private LocalDate selectedDate;/*일기날짜*/



}
