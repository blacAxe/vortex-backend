package com.vortex;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class VortexJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;
    public String status; // PENDING, PROCESSING, COMPLETED
    public LocalDateTime createdAt = LocalDateTime.now();

    // Default constructor for JPA
    public VortexJob() {}

    public VortexJob(String name, String status) {
        this.name = name;
        this.status = status;
    }
}