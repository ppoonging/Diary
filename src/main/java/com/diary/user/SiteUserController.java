package com.diary.user;


import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class SiteUserController {


    private final SiteUserSerevice siteUserSerevice;

    @GetMapping("/signup")
    public String userCreate(SiteUserForm diaryUserForm) {
        return "signup_form";
    }


    @PostMapping("/signup")
    public String userCreate(@Valid SiteUserForm diaryUserForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        if (!diaryUserForm.getPassword1().equals(diaryUserForm.getPassword2())) {
            bindingResult.rejectValue("passwword2", "passwordError", "두 개의 비밀번호가 다릅니다.");
            return "signup_form";
        }
        try {
            siteUserSerevice.create(diaryUserForm.getUsername()
                    , diaryUserForm.getPassword1(), diaryUserForm.getMyName(), diaryUserForm.getEmail(), diaryUserForm.getPhoneNumber());

        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("fall", "이미 등록된 사용자입니다");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("fall", e.getMessage());
            return "signup_form";
        }
        return "redirect:/";
    }


    @GetMapping("/login")
    public String login() {
        return "login_form";
    }



    @GetMapping("/infor")
    public String infor(Model model, Principal principal) {
        SiteUser siteUser = this.siteUserSerevice.getUser(principal.getName());
        model.addAttribute("siteUser", siteUser);
        return "ins/infor_form";
    }

    }

