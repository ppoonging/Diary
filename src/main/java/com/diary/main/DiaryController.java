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
    public String diaryCreate(DiaryForm diaryForm,@RequestParam(name = "selectedDate" ,required = false) String selectedDate,Model model) {
        model.addAttribute("selectedDate", selectedDate);
        return "diary/diaryCreate_form";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String diaryCreate( @Valid DiaryForm diaryForm,Diary diary, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "diary/diaryCreate_form";
        }
        SiteUser diaryUser = this.diaryUserSerevice.getUser(principal.getName());
        Diary d = new Diary();
            this.diaryService.createDiary(diaryForm.getSubject(), diaryForm.getContent(),diary.getSelectedDate(),diaryUser,d.getWeather());
            return "redirect:/diary/list";


    }
    /*주석
    @GetMapping("/list/{id}")
    @PreAuthorize("isAuthenticated()")
    public String list(Model model,  Principal principal
            ,@RequestParam(value = "page", defaultValue = "0") int page ) {
        Page<Diary> p = this.diaryService.getList(page);
        if(!p.get){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"저장된 데이터가 없습니다");
        }
        Page<Diary> paging =  this.diaryService.getList(page);
        model.addAttribute("paging", paging);
        this.diaryService.getList(page);
        return String.format("redirect:/diary/list/%d");

    }*/


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