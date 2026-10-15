package com.example.hrinterviewportal.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resumes")
@CrossOrigin(origins = "*")
public class ResumeController {

    private final String uploadDirectory = "uploads/resumes";

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getResume(
            @PathVariable String fileName) {

        try {

            // Resume folder
            Path uploadPath = Paths.get(uploadDirectory)
                    .toAbsolutePath()
                    .normalize();

            // Requested file
            Path filePath = uploadPath
                    .resolve(fileName)
                    .normalize();

            // Security check
            if (!filePath.startsWith(uploadPath)) {
                return ResponseEntity.badRequest().build();
            }

            // Check file exists
            if (!Files.exists(filePath) ||
                    !Files.isRegularFile(filePath)) {

                System.out.println(
                        "Resume file not found: "
                                + filePath
                );

                return ResponseEntity.notFound().build();
            }

            // Create resource
            Resource resource =
                    new UrlResource(
                            filePath.toUri()
                    );

            if (!resource.exists() ||
                    !resource.isReadable()) {

                System.out.println(
                        "Resume file cannot be read: "
                                + filePath
                );

                return ResponseEntity.notFound().build();
            }

            // Detect file type
            String contentType =
                    Files.probeContentType(filePath);

            if (contentType == null) {

                String lowerName =
                        fileName.toLowerCase();

                if (lowerName.endsWith(".pdf")) {

                    contentType =
                            "application/pdf";

                } else if (lowerName.endsWith(".doc")) {

                    contentType =
                            "application/msword";

                } else if (lowerName.endsWith(".docx")) {

                    contentType =
                            "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

                } else {

                    contentType =
                            "application/octet-stream";
                }
            }

            // PDF should open inside browser
            if (contentType.equals("application/pdf")) {

                return ResponseEntity.ok()
                        .contentType(
                                MediaType.APPLICATION_PDF
                        )
                        .header(
                                HttpHeaders.CONTENT_DISPOSITION,
                                "inline; filename=\"" +
                                        fileName +
                                        "\""
                        )
                        .body(resource);
            }

            // DOC / DOCX should download
            return ResponseEntity.ok()
                    .contentType(
                            MediaType.parseMediaType(
                                    contentType
                            )
                    )
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" +
                                    fileName +
                                    "\""
                    )
                    .body(resource);

        } catch (IOException e) {

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}