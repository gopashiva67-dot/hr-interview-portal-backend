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

import com.example.hrinterviewportal.entity.Answer;
import com.example.hrinterviewportal.service.AnswerService;

@RestController
@RequestMapping("/api/answers")
@CrossOrigin(origins = "*")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public ResponseEntity<Answer> createAnswer(
            @RequestBody Answer answer) {

        return ResponseEntity.ok(
                answerService.createAnswer(answer)
        );
    }

    @GetMapping
    public ResponseEntity<List<Answer>> getAllAnswers() {

        return ResponseEntity.ok(
                answerService.getAllAnswers()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Answer> getAnswerById(
            @PathVariable Long id) {

        return answerService.getAnswerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/interview/{interviewId}")
    public ResponseEntity<List<Answer>> getByInterview(
            @PathVariable Long interviewId) {

        return ResponseEntity.ok(
                answerService.getAnswersByInterviewId(interviewId)
        );
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<Answer>> getByQuestion(
            @PathVariable Long questionId) {

        return ResponseEntity.ok(
                answerService.getAnswersByQuestionId(questionId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(
            @PathVariable Long id) {

        answerService.deleteAnswer(id);

        return ResponseEntity.noContent().build();
    }
}