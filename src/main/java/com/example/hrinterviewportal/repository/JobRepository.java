package com.example.hrinterviewportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hrinterviewportal.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
}