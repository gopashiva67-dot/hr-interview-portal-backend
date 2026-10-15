package com.example.hrinterviewportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.hrinterviewportal.entity.Answer;
import com.example.hrinterviewportal.repository.AnswerRepository;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer createAnswer(Answer answer) {

        if (answer.getAnsweredAt() == null) {
            answer.setAnsweredAt(LocalDateTime.now());
        }

        return answerRepository.save(answer);
    }

    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    public Optional<Answer> getAnswerById(Long id) {
        return answerRepository.findById(id);
    }

    public List<Answer> getAnswersByInterviewId(Long interviewId) {
        return answerRepository.findByInterviewId(interviewId);
    }

    public List<Answer> getAnswersByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }
}