package com.vortex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private WorkerService workerService;

    @PostMapping("/submit")
    public ResponseEntity<VortexJob> submitJob(@RequestParam("file") MultipartFile file) {
        try {

            String uploadDir = "uploads/";
            Files.createDirectories(Paths.get(uploadDir));

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path filepath = Paths.get(uploadDir, filename);

            Files.write(filepath, file.getBytes());

            VortexJob job = new VortexJob(
                    file.getOriginalFilename(),
                    "PENDING",
                    filepath.toString()
            );

            job = jobRepository.save(job);

            workerService.processFile(job);

            return ResponseEntity.ok(job);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/all")
    public List<VortexJob> getAllJobs() {
        return jobRepository.findAll();
    }
}