
package com.example.hrinterviewportal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hrinterviewportal.entity.Job;
import com.example.hrinterviewportal.service.JobService;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // Create a new job
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        return ResponseEntity.ok(jobService.createJob(job));
    }

    // Get all jobs
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    // Get job by ID
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        return jobService.getJobById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update job details
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(
            @PathVariable Long id,
            @RequestBody Job job) {

        return jobService.updateJob(id, job)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Deactivate a job without deleting interview history
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Job> deactivateJob(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.deactivateJob(id));
    }

    // Activate a previously deactivated job
    @PutMapping("/{id}/activate")
    public ResponseEntity<Job> activateJob(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.activateJob(id));
    }

    // Delete a job if it has no linked interviews
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}
