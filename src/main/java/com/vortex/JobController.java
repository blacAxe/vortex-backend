package com.vortex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private WorkerService workerService;

    @PostMapping("/submit")
    public VortexJob submitJob(@RequestBody Map<String, String> payload) {
        String jobName = payload.getOrDefault("name", "Unknown Job");
        
        // Save the job to DB initially as PENDING
        VortexJob job = jobRepository.save(new VortexJob(jobName, "PENDING"));
        
        // Hand off the saved job to the worker
        workerService.processVideo(job);
        
        return job; // Returns the JSON with the ID and Status
    }

    @GetMapping("/all")
    public List<VortexJob> getAllJobs() {
        return jobRepository.findAll();
    }
}
