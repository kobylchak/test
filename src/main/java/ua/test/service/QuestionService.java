package ua.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.test.dao.Module;
import org.springframework.data.domain.Pageable;
import ua.test.dao.ModuleRepository;
import ua.test.dao.Question;
import ua.test.dao.QuestionRepository;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    @Transactional
    public void addQuestion(Question question) {
        questionRepository.save(question);
    }

    @Transactional
    public void addModule(Module module) {
        moduleRepository.save(module);
    }

    @Transactional
    public void deleteQuestions(long[] idList) {
        for (long id : idList)
            questionRepository.deleteById(id);
    }

    @Transactional()
    public List<Module> findModules() {
        return moduleRepository.findAll();
    }

    @Transactional(readOnly=true)
    public List<Question> findAll(Pageable pageable) {
        return questionRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Transactional(readOnly=true)
    public List<Question> findByModule(Module module, Pageable pageable) {
        return questionRepository.findByModule(module, pageable);
    }

    @Transactional(readOnly = true)
    public long countByModule(Module module) {
        return questionRepository.countByModule(module);
    }

    @Transactional(readOnly = true)
    public List<Question> findByPattern(String pattern, Pageable pageable) {
        return questionRepository.findByPattern(pattern, pageable);
    }

    @Transactional(readOnly = true)
    public long count() {
        return questionRepository.count();
    }

    @Transactional(readOnly = true)
    public Module findModule(long id) {
        return moduleRepository.getOne(id);
    }
}