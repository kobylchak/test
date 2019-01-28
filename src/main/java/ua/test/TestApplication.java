package ua.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ua.test.dao.CustomUser;
import ua.test.dao.Module;
import ua.test.dao.Question;
import ua.test.dao.UserRole;
import ua.test.service.QuestionService;
import ua.test.service.UserService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(final QuestionService questionService, final UserService userService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                userService.addUser(new CustomUser("admin", "$2a$10$QTLi3Raw8MPC1XTk83d63.n8STr3p.hgqGwhD1yWjmzTU0XmRuj36", UserRole.ADMIN));
                userService.addUser(new CustomUser("user", "$2a$10$s0nvPz6NO8oOvIvFYljjJekQ3BkXgDy2NZsjOawQH8Gj1cNfLp9SO", UserRole.USER));
                StringBuilder sb = new StringBuilder();
                File catalog = new File("modules");
                File[] fileArray = catalog.listFiles();
                for (File file : fileArray) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String str = "";
                        for (; (str = br.readLine()) != null; ) {
                            sb.append(str);
                        }
                        String mod = file.getName().substring(0, file.getName().lastIndexOf('.'));
                        Module module = new Module(mod);
                        questionService.addModule(module);
                        String[] questioins = sb.toString().split("!!!!!");
                        for (String questioin : questioins) {
                            String[] questAnswerFull = questioin.split("QQQ");
                            String quest = questAnswerFull[0];
                            String answer = questAnswerFull[1];
                            String fullAnswer = questAnswerFull[2];
                            questionService.addQuestion(new Question(module, quest, answer, fullAnswer));
                        }
                        sb = new StringBuilder("");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
    }
}

