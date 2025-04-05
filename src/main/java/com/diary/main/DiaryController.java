package com.diary.main;


import com.diary.DataNotFoundException;
import com.diary.user.SiteUser;
import com.diary.user.SiteUserSerevice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDate;


@RequiredArgsConstructor
@Controller
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;
    private final SiteUserSerevice diaryUserSerevice;

    @GetMapping("/main")
    public String mainDiary() {
        return "diary/mainDiary_form";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String diaryCreate(DiaryForm diaryForm, @RequestParam(name = "selectedDate", required = false) String selectedDate,
                              @RequestParam(name = "weather", required = false) String weather, Model model) {
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("weather", weather);//날씨 저장
        return "diary/diaryCreate_form";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String diaryCreate(@Valid DiaryForm diaryForm, Diary diary, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "diary/diaryCreate_form";
        }
        SiteUser diaryUser = this.diaryUserSerevice.getUser(principal.getName());


        this.diaryService.createDiary(diaryForm.getSubject(), diaryForm.getContent(), diary.getWeather(), diary.getSelectedDate(), diaryUser);
        return "redirect:/diary/list";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page, Principal principal) {


        Page<Diary> paging = this.diaryService.getList(page);
        String username = principal.getName();
        int mScore = diaryService.weatherScore();
        int myMood = diaryService.getUserMood(username);
        int allMood = diaryService.getAllMonthlyMood();
        model.addAttribute("mScore", mScore);// 오늘 기분지수
        model.addAttribute("myMood", myMood); //로그인한 유저 기분지수
        model.addAttribute("allMood", allMood);//이번달 모든 유저 기분지수
        model.addAttribute("paging", paging);

        return "diary/diary_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mylist")
    public String myList(Model model, @RequestParam(value = "page", defaultValue = "0") int page, Principal principal) {
        String username = principal.getName();
        Page<Diary> myPaging = this.diaryService.getMyList(username, page);

        int mScore = diaryService.weatherScore();
        int myMood = diaryService.getUserMood(username);
        model.addAttribute("paging", myPaging);
        model.addAttribute("mScore", mScore);
        model.addAttribute("myMood", myMood);
        return "diary/mypage"; // 같은 템플릿 재사용 가능
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, DiaryForm diaryForm) {
        Diary d = this.diaryService.getDiary(id);
        model.addAttribute("diary", d);
        return "diary/detail_form";
    }


    /*수정*/

    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modifyDiary(Principal principal, @PathVariable("id") Integer id,
                              DiaryForm diaryForm, Model model) {
        Diary diary = this.diaryService.getDiary(id);

        if (!diary.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정할 권한이 없습니다");
        }

        // 제목과 내용만 DiaryForm에 설정
        diaryForm.setSubject(diary.getSubject());
        diaryForm.setContent(diary.getContent());

        // 날짜/날씨는 따로 모델에 넣는다
        model.addAttribute("selectedDate", diary.getSelectedDate());
        model.addAttribute("weather", diary.getWeather());
        model.addAttribute("diaryId", id);

        return "diary/diaryCreate_form";
    }

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modifyDiary(@Valid DiaryForm diaryForm, BindingResult bindingResult,
                              @PathVariable("id") Integer id,
                              @RequestParam("selectedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate,
                              @RequestParam("weather") String weather,
                              Principal principal) {

        if (bindingResult.hasErrors()) {
            return "diary/diaryCreate_form";
        }

        Diary diary = this.diaryService.getDiary(id);
        if (!diary.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다");
        }

        this.diaryService.modify(diary, diaryForm.getSubject(), diaryForm.getContent(), weather, selectedDate);

        return "redirect:/diary/detail/" + id;
    }
    @GetMapping("/test")
    public String test(){

        return "test";
    }

}
