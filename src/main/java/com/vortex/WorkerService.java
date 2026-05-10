package com.vortex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class WorkerService {

    @Autowired
    private JobRepository jobRepository;

    @Async
    public void processFile(VortexJob job) {

        try {

            job.status = "PROCESSING";
            jobRepository.save(job);

            String content = Files.readString(Paths.get(job.filepath));

            String normalized = content.toLowerCase();

            job.result = "SAFE";
            job.summary = "No suspicious patterns detected.";

            if (normalized.contains("union select")) {

                job.result = "SQL_INJECTION";
                job.summary = "SQL injection indicators detected.";
                job.severity = "CRITICAL";

            } else if (
                    normalized.contains("powershell") ||
                    normalized.contains("invoke-expression")
            ) {

                job.result = "POWERSHELL";
                job.summary = "PowerShell execution patterns detected.";
                job.severity = "HIGH";

            } else if (
                    normalized.contains("cmd.exe")
            ) {

                job.result = "COMMAND_INJECTION";
                job.summary = "Command execution patterns detected.";
                job.severity = "HIGH";

            } else if (
                    normalized.contains("<script>")
            ) {

                job.result = "XSS";
                job.summary = "Cross-site scripting payload detected.";
                job.severity = "MEDIUM";

            } else if (
                    normalized.contains("encrypt") ||
                    normalized.contains("bitcoin") ||
                    normalized.contains("decrypt") ||
                    normalized.contains("ransom")
            ) {

                job.result = "RANSOMWARE";
                job.summary = "Ransomware-related indicators detected.";
                job.severity = "CRITICAL";

            } else {

                job.result = "SAFE";
                job.summary = "No suspicious patterns detected.";
                job.severity = "LOW";
            }

            job.status = "COMPLETED";

            jobRepository.save(job);

            System.out.println("[VORTEX] Scan Completed: " + job.name);

        } catch (Exception e) {

            job.status = "FAILED";
            job.result = "ERROR";
            job.summary = e.getMessage();

            jobRepository.save(job);
        }
    }
}