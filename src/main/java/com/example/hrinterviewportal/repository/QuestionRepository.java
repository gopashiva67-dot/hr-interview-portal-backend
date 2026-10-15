package com.example.hrinterviewportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hrinterviewportal.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByJobId(Long jobId);
}