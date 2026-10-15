
package com.example.hrinterviewportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.hrinterviewportal.entity.Job;
import com.example.hrinterviewportal.repository.InterviewRepository;
import com.example.hrinterviewportal.repository.JobRepository;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final InterviewRepository interviewRepository;

    public JobService(
            JobRepository jobRepository,
            InterviewRepository interviewRepository) {

        this.jobRepository = jobRepository;
        this.interviewRepository = interviewRepository;
    }

    public Job createJob(Job job) {
        if (job.getStatus() == null || job.getStatus().isBlank()) {
            job.setStatus("ACTIVE");
        }

        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    public Optional<Job> updateJob(Long id, Job updatedJob) {
        return jobRepository.findById(id).map(existingJob -> {
            existingJob.setTitle(updatedJob.getTitle());
            existingJob.setDescription(updatedJob.getDescription());
            existingJob.setRequirements(updatedJob.getRequirements());
            existingJob.setCompanyName(updatedJob.getCompanyName());

            return jobRepository.save(existingJob);
        });
    }

    public Job deactivateJob(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Job not found."
                ));

        job.setStatus("INACTIVE");
        return jobRepository.save(job);
    }

    public Job activateJob(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Job not found."
                ));

        job.setStatus("ACTIVE");
        return jobRepository.save(job);
    }

    public void deleteJob(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Job not found."
            );
        }

        if (interviewRepository.existsByJobId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "This job has linked interviews. Deactivate it instead."
            );
        }

        jobRepository.deleteById(id);
    }
}
