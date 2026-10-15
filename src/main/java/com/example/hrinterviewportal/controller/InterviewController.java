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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrinterviewportal.entity.Interview;
import com.example.hrinterviewportal.service.InterviewService;

@RestController
@RequestMapping("/api/interviews")
@CrossOrigin(origins = "*")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping
    public ResponseEntity<Interview> createInterview(
            @RequestBody Interview interview) {

        return ResponseEntity.ok(
                interviewService.createInterview(interview)
        );
    }

    @GetMapping
    public ResponseEntity<List<Interview>> getAllInterviews() {

        return ResponseEntity.ok(
                interviewService.getAllInterviews()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interview> getInterviewById(
            @PathVariable Long id) {

        return interviewService.getInterviewById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<Interview>> getByCandidate(
            @PathVariable Long candidateId) {

        return ResponseEntity.ok(
                interviewService.getInterviewsByCandidateId(candidateId)
        );
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Interview>> getByJob(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                interviewService.getInterviewsByJobId(jobId)
        );
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Interview> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Interview updated =
                interviewService.updateInterviewStatus(id, status);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterview(
            @PathVariable Long id) {

        interviewService.deleteInterview(id);

        return ResponseEntity.noContent().build();
    }
}