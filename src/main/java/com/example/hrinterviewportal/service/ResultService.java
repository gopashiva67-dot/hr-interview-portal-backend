package com.example.hrinterviewportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.hrinterviewportal.entity.Result;
import com.example.hrinterviewportal.repository.ResultRepository;

@Service
public class ResultService {

    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public Result createResult(Result result) {

        if (result.getStatus() == null ||
                result.getStatus().isBlank()) {
            result.setStatus("PENDING");
        }

        if (result.getEvaluatedAt() == null &&
                result.getScore() != null) {
            result.setEvaluatedAt(LocalDateTime.now());
        }

        return resultRepository.save(result);
    }

    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    public Optional<Result> getResultById(Long id) {
        return resultRepository.findById(id);
    }

    public Optional<Result> getResultByInterviewId(Long interviewId) {
        return resultRepository.findByInterviewId(interviewId);
    }

    public Result updateResult(Long id, Result updatedResult) {

        Optional<Result> optionalResult =
                resultRepository.findById(id);

        if (optionalResult.isEmpty()) {
            return null;
        }

        Result result = optionalResult.get();

        result.setScore(updatedResult.getScore());
        result.setFeedback(updatedResult.getFeedback());
        result.setStatus(updatedResult.getStatus());

        if (updatedResult.getScore() != null) {
            result.setEvaluatedAt(LocalDateTime.now());
        }

        return resultRepository.save(result);
    }

    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }
}