package com.example.hrinterviewportal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hrinterviewportal.entity.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {

    Optional<Result> findByInterviewId(Long interviewId);
}