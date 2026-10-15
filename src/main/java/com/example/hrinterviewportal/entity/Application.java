package com.example.hrinterviewportal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Long jobId;


    @Column(nullable = false)
    private Long userId;


    @Column(nullable = false)
    private String fullName;


    @Column(nullable = false)
    private String email;


    @Column(nullable = false)
    private String phone;


    @Column(nullable = false)
    private String experience;


    @Column(columnDefinition = "TEXT")
    private String coverLetter;


    @Column(nullable = false)
    private String status = "PENDING";


    // =====================================================
    // RESUME
    // =====================================================

    @Column(name = "resume_file")
    private String resumeFile;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public Application() {
    }


    // =====================================================
    // GETTERS AND SETTERS
    // =====================================================

    public Long getId() {

        return id;
    }


    public void setId(Long id) {

        this.id = id;
    }


    public Long getJobId() {

        return jobId;
    }


    public void setJobId(Long jobId) {

        this.jobId = jobId;
    }


    public Long getUserId() {

        return userId;
    }


    public void setUserId(Long userId) {

        this.userId = userId;
    }


    public String getFullName() {

        return fullName;
    }


    public void setFullName(String fullName) {

        this.fullName = fullName;
    }


    public String getEmail() {

        return email;
    }


    public void setEmail(String email) {

        this.email = email;
    }


    public String getPhone() {

        return phone;
    }


    public void setPhone(String phone) {

        this.phone = phone;
    }


    public String getExperience() {

        return experience;
    }


    public void setExperience(String experience) {

        this.experience = experience;
    }


    public String getCoverLetter() {

        return coverLetter;
    }


    public void setCoverLetter(String coverLetter) {

        this.coverLetter = coverLetter;
    }


    public String getStatus() {

        return status;
    }


    public void setStatus(String status) {

        this.status = status;
    }


    public String getResumeFile() {

        return resumeFile;
    }


    public void setResumeFile(String resumeFile) {

        this.resumeFile = resumeFile;
    }
}