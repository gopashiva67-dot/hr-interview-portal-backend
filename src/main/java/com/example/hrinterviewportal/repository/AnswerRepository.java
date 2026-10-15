package com.example.hrinterviewportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hrinterviewportal.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByInterviewId(Long interviewId);

    List<Answer> findByQuestionId(Long questionId);
}