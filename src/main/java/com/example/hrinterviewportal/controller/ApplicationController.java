package com.example.hrinterviewportal.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.hrinterviewportal.entity.Application;
import com.example.hrinterviewportal.repository.ApplicationRepository;
import com.example.hrinterviewportal.service.ResumeService;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ResumeService resumeService;


    // =====================================================
    // CREATE APPLICATION WITHOUT RESUME
    // =====================================================

    @PostMapping
    public Application createApplication(
            @RequestBody Application application) {

        if (application.getStatus() == null ||
                application.getStatus().isEmpty()) {

            application.setStatus("PENDING");
        }

        return applicationRepository.save(application);
    }


    // =====================================================
    // CREATE APPLICATION WITH RESUME
    // =====================================================

    @PostMapping(
            value = "/with-resume",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Application createApplicationWithResume(

            @RequestParam Long jobId,

            @RequestParam Long userId,

            @RequestParam String fullName,

            @RequestParam String email,

            @RequestParam String phone,

            @RequestParam String experience,

            @RequestParam(
                    required = false
            )
            String coverLetter,

            @RequestParam("resume")
            MultipartFile resume

    ) throws IOException {


        // Create application object

        Application application =
                new Application();


        // Set application details

        application.setJobId(jobId);

        application.setUserId(userId);

        application.setFullName(fullName);

        application.setEmail(email);

        application.setPhone(phone);

        application.setExperience(experience);

        application.setCoverLetter(coverLetter);

        application.setStatus("PENDING");


        // =================================================
        // SAVE RESUME
        // =================================================

        String resumeFileName =
                resumeService.saveResume(resume);


        // Save resume file name into database

        application.setResumeFile(
                resumeFileName
        );


        // =================================================
        // SAVE APPLICATION
        // =================================================

        return applicationRepository.save(
                application
        );
    }


    // =====================================================
    // GET ALL APPLICATIONS
    // =====================================================

    @GetMapping
    public List<Application> getAllApplications() {

        return applicationRepository.findAll();
    }


    // =====================================================
    // GET APPLICATION BY ID
    // =====================================================

    @GetMapping("/{id}")
    public Application getApplicationById(
            @PathVariable Long id) {

        return applicationRepository
                .findById(id)
                .orElse(null);
    }


    // =====================================================
    // UPDATE APPLICATION STATUS
    // =====================================================

    @PutMapping("/{id}")
    public Application updateApplicationStatus(

            @PathVariable Long id,

            @RequestBody
            Application updatedApplication

    ) {

        Application existingApplication =
                applicationRepository
                        .findById(id)
                        .orElse(null);


        if (existingApplication == null) {

            return null;
        }


        if (updatedApplication.getStatus() != null &&
                !updatedApplication.getStatus().isEmpty()) {

            existingApplication.setStatus(
                    updatedApplication.getStatus()
            );
        }


        return applicationRepository.save(
                existingApplication
        );
    }


    // =====================================================
    // DELETE APPLICATION
    // =====================================================

    @DeleteMapping("/{id}")
    public String deleteApplication(
            @PathVariable Long id) {

        applicationRepository.deleteById(id);

        return "Application deleted successfully";
    }
}