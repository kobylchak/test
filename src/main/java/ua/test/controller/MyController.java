package ua.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.test.dao.CustomUser;
import ua.test.dao.Module;
import ua.test.dao.Question;
import ua.test.dao.UserRole;
import ua.test.service.QuestionService;
import ua.test.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Controller
public class MyController {
    static final int DEFAULT_GROUP_ID = -1;
    static final int ITEMS_PER_PAGE = 6;
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String index(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        if (login.equals("admin")) model.addAttribute("admin", "admin");
        else model.addAttribute("user", "user");
        CustomUser dbUser = userService.getUserByLogin(login);
        model.addAttribute("login", login);
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("email", dbUser.getEmail());
        model.addAttribute("phone", dbUser.getPhone());
        return "index";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam(required = false) String email, @RequestParam(required = false) String phone) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser dbUser = userService.getUserByLogin(login);
        dbUser.setEmail(email);
        dbUser.setPhone(phone);
        userService.updateUser(dbUser);
        return "redirect:/";
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String update(@RequestParam String login,
                         @RequestParam String password,
                         @RequestParam(required = false) String email,
                         @RequestParam(required = false) String phone,
                         Model model) {
        if (userService.existsByLogin(login)) {
            model.addAttribute("exists", true);
            return "register";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passHash = passwordEncoder.encode(password);
        CustomUser dbUser = new CustomUser(login, passHash, UserRole.USER, email, phone);
        userService.addUser(dbUser);
        return "redirect:/";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("login", user.getUsername());
        return "unauthorized";
    }

    @RequestMapping("/admin")
    public String admin(Model model, @RequestParam(required = false, defaultValue = "0") Integer page) {

        if (page < 0) page = 0;

        List<Question> questions = questionService
                .findAll(new PageRequest(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));


        model.addAttribute("modules", questionService.findModules());

        model.addAttribute("allPages", getPageCount());

        model.addAttribute("questions",questions);
        return "admin";
    }

    @RequestMapping("/question_add_page")
    public String questionAddPage(Model model) {
        model.addAttribute("modules", questionService.findModules());
        return "question_add_page";
    }

    @RequestMapping("/module_add_page")
    public String moduleAddPage() {
        return "module_add_page";
    }

    @RequestMapping("/module/{id}")
    public String listModule(@PathVariable(value = "id") long moduleId,
                             @RequestParam(required = false, defaultValue = "0") Integer page,
                             Model model) {
        Module module = (moduleId != DEFAULT_GROUP_ID) ? questionService.findModule(moduleId) : null;
        if (page < 0) page = 0;

        List<Question> questions = questionService.findByModule(module, new PageRequest(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));
        model.addAttribute("modules", questionService.findModules());
        model.addAttribute("questions", questions);
        model.addAttribute("byGroupPages", getPageCount(module));
        model.addAttribute("moduleId", moduleId);
        return "admin";
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@RequestParam String pattern, Model model) {
        model.addAttribute("modules", questionService.findModules());
        model.addAttribute("questions", questionService.findByPattern(pattern, null));
        return "admin";
    }

    @RequestMapping(value = "/question/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> delete(@RequestParam(value = "toDelete[]", required = false) long[] toDelete) {
        if (toDelete != null && toDelete.length > 0)
            questionService.deleteQuestions(toDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/question/add", method = RequestMethod.POST)
    public String questionAdd(@RequestParam(value = "module") long moduleId,
                              @RequestParam String question,
                              @RequestParam String answer,
                              @RequestParam String fullAnswer
    ) {
        Module module = (moduleId != DEFAULT_GROUP_ID) ? questionService.findModule(moduleId) : null;
        Question quest = new Question(module, question, answer, fullAnswer);
        questionService.addQuestion(quest);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/module/add", method = RequestMethod.POST)
    public String moduleAdd(@RequestParam String name) {
        questionService.addModule(new Module(name));
        return "redirect:/admin";
    }

    private long getPageCount() {
        long totalCount = questionService.count();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private long getPageCount(Module module) {
        long totalCount = questionService.countByModule(module);
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
}