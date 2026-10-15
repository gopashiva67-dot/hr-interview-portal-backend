package com.example.hrinterviewportal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrinterviewportal.entity.Result;
import com.example.hrinterviewportal.service.ResultService;

@RestController
@RequestMapping("/api/results")
@CrossOrigin(origins = "*")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping
    public ResponseEntity<Result> createResult(
            @RequestBody Result result) {

        return ResponseEntity.ok(
                resultService.createResult(result)
        );
    }

    @GetMapping
    public ResponseEntity<List<Result>> getAllResults() {

        return ResponseEntity.ok(
                resultService.getAllResults()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getResultById(
            @PathVariable Long id) {

        return resultService.getResultById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/interview/{interviewId}")
    public ResponseEntity<Result> getResultByInterviewId(
            @PathVariable Long interviewId) {

        return resultService.getResultByInterviewId(interviewId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result> updateResult(
            @PathVariable Long id,
            @RequestBody Result result) {

        Result updated =
                resultService.updateResult(id, result);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResult(
            @PathVariable Long id) {

        resultService.deleteResult(id);

        return ResponseEntity.noContent().build();
    }
}