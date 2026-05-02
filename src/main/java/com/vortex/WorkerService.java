package com.vortex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class WorkerService {

    @Async
    public CompletableFuture<Void> processVideo(String jobName) {
        return CompletableFuture.runAsync(() -> {
            try {
                System.out.println("[WORKER] Starting heavy task: " + jobName);
                
                // Simulate real work for 5 seconds
                Thread.sleep(5000); 
                
                System.out.println("[WORKER] Task Finished: " + jobName);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    @Autowired
    private JobRepository jobRepository;

    @Async
    public void processVideo(VortexJob job) {
        try {
            // Update to PROCESSING
            job.status = "PROCESSING";
            jobRepository.save(job);
            
            Thread.sleep(5000); // Simulate Work
            
            // Update to COMPLETED
            job.status = "COMPLETED";
            jobRepository.save(job);
            System.out.println("DB Updated: " + job.name + " is COMPLETED");
        } catch (Exception e) {
            job.status = "FAILED";
            jobRepository.save(job);
        }
    }
}
