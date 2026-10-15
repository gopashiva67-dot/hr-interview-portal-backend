package com.example.hrinterviewportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.hrinterviewportal.entity.Interview;
import com.example.hrinterviewportal.repository.InterviewRepository;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;

    public InterviewService(InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    public Interview createInterview(Interview interview) {

        if (interview.getStatus() == null ||
                interview.getStatus().isBlank()) {
            interview.setStatus("SCHEDULED");
        }

        return interviewRepository.save(interview);
    }

    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    public Optional<Interview> getInterviewById(Long id) {
        return interviewRepository.findById(id);
    }

    public List<Interview> getInterviewsByCandidateId(Long candidateId) {
        return interviewRepository.findByCandidateId(candidateId);
    }

    public List<Interview> getInterviewsByJobId(Long jobId) {
        return interviewRepository.findByJobId(jobId);
    }

    public Interview updateInterviewStatus(Long id, String status) {

        Optional<Interview> optionalInterview =
                interviewRepository.findById(id);

        if (optionalInterview.isEmpty()) {
            return null;
        }

        Interview interview = optionalInterview.get();
        interview.setStatus(status);

        return interviewRepository.save(interview);
    }

    public void deleteInterview(Long id) {
        interviewRepository.deleteById(id);
    }
}