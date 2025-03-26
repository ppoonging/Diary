package com.diary.main;


import com.diary.user.SiteUser;
import com.diary.user.SiteUserSerevice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


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
    public String diaryCreate(DiaryForm diaryForm,@RequestParam(name = "selectedDate" ,required = false) String selectedDate,
                             @RequestParam(name = "weather",required = false) String weather, Model model) {
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("weather", weather);//날씨 저장
        return "diary/diaryCreate_form";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String diaryCreate( @Valid DiaryForm diaryForm,Diary diary, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "diary/diaryCreate_form";
        }
        SiteUser diaryUser = this.diaryUserSerevice.getUser(principal.getName());


            this.diaryService.createDiary(diaryForm.getSubject(), diaryForm.getContent(),diary.getWeather(),diary.getSelectedDate(),diaryUser);
            return "redirect:/diary/list";

    }

     @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page",defaultValue = "0") int page) {
        Page<Diary> paging =  this.diaryService.getList(page);
        model.addAttribute("paging", paging);
        return "diary/diary_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id,DiaryForm diaryForm) {
         Diary d = this.diaryService.getDiary(id);
         model.addAttribute("diary", d);
         return "diary/detail_form";
    }
}