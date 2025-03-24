package com.diary.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserForm {
    @NotEmpty(message = "닉네임을 입력하세요")
    private String myName; //닉네임

    @NotEmpty(message = "아이디를 입력하세요")
    private String username; //아이디

    @NotEmpty(message = "비밀번호를 입력하세요")
    private String password1;

    @NotEmpty(message = "비밀번호를 확인하세요")
    private String password2;

    @Email
    @NotEmpty(message = "이메일을 입력하세요")
    private String email;

    @NotEmpty(message = "전화번호를 압력하세요")
    private String phoneNumber;





}
