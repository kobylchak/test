package ua.test.dao;

import javax.persistence.*;

@Entity
@Table(name = "Questions")
public class Question {
    @Id
    @GeneratedValue
    private long id;
    private String question;
    private String fullAnswer;
    private String answer;
    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    public Question() {
    }

    public Question(Module module, String question, String answer, String fullAnswer) {
        this.question = question;
        this.fullAnswer = fullAnswer;
        this.answer = answer;
        this.module = module;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFullAnswer() {
        return fullAnswer;
    }

    public void setFullAnswer(String fullAnswer) {
        this.fullAnswer = fullAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
