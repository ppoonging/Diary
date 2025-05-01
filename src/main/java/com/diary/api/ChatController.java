package com.diary.api;




import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class ChatController {

    private final OpenAiService openAiService;

    public ChatController(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @GetMapping("/chat")
    public String chat() {
       return "chat";
   }
   @PostMapping("/chat")
    public String chat(@RequestParam("question") String question, Model model) {
       String answer = openAiService.ask(question);
       model.addAttribute("question", question);
       model.addAttribute("answer", answer);
       return "chat";
   }
    @PostMapping("/chat/ask")
    @ResponseBody
    public String askAjax(@RequestParam("question") String question) {
        return openAiService.ask(question);
    }
}