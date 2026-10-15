package com.example.hrinterviewportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.hrinterviewportal.entity.Question;
import com.example.hrinterviewportal.repository.QuestionRepository;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public List<Question> getQuestionsByJobId(Long jobId) {
        return questionRepository.findByJobId(jobId);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}