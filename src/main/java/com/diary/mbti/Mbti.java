package com.diary.mbti;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

@Entity
@Getter
@Setter
public class Mbti {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mbtiId;

    private String maleMbti;

    private String femaleMbti;

    private Integer score;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String description;


}
