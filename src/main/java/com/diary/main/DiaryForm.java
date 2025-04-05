package com.diary.main;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DiaryForm {

    @Size(min = 1, max = 20)
    @NotEmpty(message = "제목을 입력해주세요")
    private String subject;


    @NotEmpty(message = "내용을 입력해주세요")
    private String content;

    @NotNull(message = "달력에 체크해주세요")
    private LocalDate selectedDate;/*일기날짜*/

    @NotEmpty(message = "오늘의 기분을 날씨로 표현해주세요")
    private String weather;


}
