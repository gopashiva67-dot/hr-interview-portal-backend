package com.example.hrinterviewportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hrinterviewportal.entity.Application;

@Repository
public interface ApplicationRepository
        extends JpaRepository<Application, Long> {
}