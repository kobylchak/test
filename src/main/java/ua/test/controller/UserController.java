package ua.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.test.dao.Module;
import ua.test.dao.Question;
import ua.test.service.QuestionService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class UserController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("modules", questionService.findModules());
        return "test";
    }

    @RequestMapping("/module_user/{module.id}")
    public String listUserModule(@PathVariable(value = "module.id") long moduleId,
                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                 Model model) {
        Module module = questionService.findModule(moduleId);
        List<Question> questions = questionService.findByModule(module, null);
        model.addAttribute("modules", questionService.findModules());
        model.addAttribute("questions", questions);
        model.addAttribute("modulok", moduleId);
        return "test";
    }

    @PostMapping("/check_answer")
    public String check(Model model,
                        @RequestParam(value = "modulok") long moduleId,
                        @RequestParam("answer[]") List<String> toCheck,
                        @RequestParam(required = false, defaultValue = "0") Integer page) {
        List<Question> listAnswer = new CopyOnWriteArrayList<>();
        Module module = questionService.findModule(moduleId);
        String moduleName = module.getName();
        model.addAttribute("moduleName", moduleName);
        for (int i = 0; i < module.getModules().size(); i++) {
            String question = module.getModules().get(i).getQuestion();
            String answerAdmin = module.getModules().get(i).getAnswer();
            String answerUser = toCheck.get(i);
            String fullAnswer = module.getModules().get(i).getFullAnswer();
            if (!answerAdmin.equals(answerUser)) {
                listAnswer.add(new Question(module, question, answerAdmin, fullAnswer));
            } else {
                listAnswer.add(new Question(module, question, answerUser, "Ответ верный."));
            }
        }
        model.addAttribute("listAnswer", listAnswer);
        return "result";
    }
}
