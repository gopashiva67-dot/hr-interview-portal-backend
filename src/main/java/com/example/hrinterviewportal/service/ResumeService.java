package com.example.hrinterviewportal.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeService {

    private final String uploadDirectory = "uploads/resumes";

    public String saveResume(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IOException("Resume file is empty.");
        }

        Path uploadPath = Paths.get(uploadDirectory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null || originalFileName.isBlank()) {
            throw new IOException("Invalid resume file name.");
        }

        // Remove unsafe path information
        String fileName = Paths.get(originalFileName)
                .getFileName()
                .toString();

        Path filePath = uploadPath.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        return fileName;
    }
}