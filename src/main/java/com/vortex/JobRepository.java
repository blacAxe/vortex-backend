package com.vortex;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<VortexJob, Long> {
}