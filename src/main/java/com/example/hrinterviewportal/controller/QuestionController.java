package com.example.hrinterviewportal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrinterviewportal.entity.Question;
import com.example.hrinterviewportal.service.QuestionService;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(
            @RequestBody Question question) {
        return ResponseEntity.ok(questionService.createQuestion(question));
    }

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(
            @PathVariable Long id) {

        return questionService.getQuestionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Question>> getQuestionsByJobId(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                questionService.getQuestionsByJobId(jobId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable Long id) {

        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}