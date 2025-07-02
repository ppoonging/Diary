package com.diary.mbti;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mbti")
public class MbtiController {

    private final MbtiService mbtiService;

    @GetMapping("/choose")
    public String choose() {
        return "mbti/mbti_form";
    }

    @GetMapping("/result")
    public String mbtiResult(@RequestParam String male, @RequestParam String female, Model model) {
        Mbti result = mbtiService.getCompatibility(male, female);
        model.addAttribute("result", result);
        return "mbti/mbtiResult"; // 결과 페이지 (html)
    }
}

