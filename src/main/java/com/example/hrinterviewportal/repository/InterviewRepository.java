
package com.example.hrinterviewportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hrinterviewportal.entity.Interview;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

    // Get interviews by candidate ID
    List<Interview> findByCandidateId(Long candidateId);

    // Get interviews by job ID
    List<Interview> findByJobId(Long jobId);

    // Check whether a job has linked interviews
    boolean existsByJobId(Long jobId);
}
