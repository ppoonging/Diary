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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diary_seq")
    @SequenceGenerator(name = "diary_seq",sequenceName = "diary_seq",allocationSize = 1)
    private Integer id;
    @Column(length = 30)
    private String username;
    @Column(length = 30)
    private String subject;
    @Lob
    private String content;

    private String weather;

    @ManyToOne
    private SiteUser author; /*이걸 기준으로 수정 권한 가져올거*/
    @Column(name = "create_date_time")
    private LocalDateTime createDateTime; /*작성일*/

    private LocalDateTime modifyDateTime; /*수정일*/

    private LocalDate selectedDate;/*일기날짜*/



}
