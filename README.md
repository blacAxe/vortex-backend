# VORTEX

Distributed Malware Analysis and Threat Detection Service built with Spring Boot.

VORTEX is a modular backend security service designed to analyze uploaded files, detect malicious patterns, classify threats, and expose analytics APIs for integration into larger security platforms.

It operates as an internal microservice behind the Sentinel Proxy gateway and powers the VORTEX dashboard inside Sentinel OS.

---

# Features

- Asynchronous file scanning pipeline
- Malware and attack pattern detection
- Threat severity classification
- Worker-based processing model
- REST API for uploads and analytics
- PostgreSQL persistence
- Real-time dashboard integration
- JWT protected access through reverse proxy
- Distributed-ready architecture

---

# Detection Engine

The detection engine scans uploaded file contents for suspicious indicators and attack signatures.

Current supported detections include:

- SQL Injection
- Cross Site Scripting (XSS)
- PowerShell Execution
- Command Injection
- Ransomware Indicators

Each detection produces:

- Result classification
- Severity level
- Summary
- Scan metadata
- Worker attribution
- Processing timestamps

---

# Severity Levels

| Severity | Description |
|---|---|
| LOW | Benign or safe content |
| MEDIUM | Suspicious but lower impact |
| HIGH | Dangerous execution indicators |
| CRITICAL | Severe malicious payloads |

Example mappings:

| Detection | Severity |
|---|---|
| SQL Injection | CRITICAL |
| Ransomware Indicators | CRITICAL |
| PowerShell Execution | HIGH |
| XSS Payload | MEDIUM |

---

# Architecture

VORTEX is designed as a service inside a larger distributed security platform.

Frontend requests flow through:

```text
Next.js Frontend
        ↓
Sentinel Proxy (Go Gateway + JWT)
        ↓
VORTEX Service (Spring Boot)
        ↓
PostgreSQL
```

The service is intentionally isolated behind the gateway layer to support:

- centralized authentication
- rate limiting
- request tracing
- security middleware
- future event streaming

---

# Tech Stack

## Backend

- Java 23
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL

## Infrastructure

- Docker
- Docker Compose

## Platform Integration

- Sentinel Proxy (Go API Gateway)
- Sentinel OS Dashboard
- JWT Authentication
- Redpanda Event Pipeline

---

# API Endpoints

## Submit Scan Job

```http
POST /api/jobs/submit
```

Uploads a file and creates an asynchronous scan job.

---

## Retrieve All Jobs

```http
GET /api/jobs/all
```

Returns all scan jobs and metadata.

---

## Retrieve Statistics

```http
GET /api/jobs/stats
```

Returns dashboard statistics including:

- total jobs
- completed jobs
- failed jobs
- active workers

---

## Retrieve Analytics

```http
GET /api/jobs/analytics
```

Returns aggregated threat analytics by severity level.

---

# Job Lifecycle

Each uploaded file progresses through a processing lifecycle.

```text
QUEUED
   ↓
PROCESSING
   ↓
COMPLETED / FAILED
```

Worker services asynchronously process scan jobs and update results in the database.

---

# Example Detection Result

```json
{
  "id": 12,
  "name": "payload.txt",
  "result": "SQL_INJECTION",
  "severity": "CRITICAL",
  "status": "COMPLETED",
  "workerNode": "worker-alpha",
  "scanDurationMs": 27
}
```

---

# Running Locally

## Start with Maven

```bash
./mvnw spring-boot:run
```

---

## Docker Deployment

The service is designed to run inside the Sentinel Platform Docker environment.

```bash
docker compose up --build
```

---

# Example Test Payloads

## SQL Injection

```text
' UNION SELECT password FROM users --
```

## XSS Payload

```html
<script>alert('xss')</script>
```

## PowerShell Execution

```powershell
powershell Invoke-Expression payload
```

## Ransomware Indicators

```text
encrypt bitcoin decrypt ransom
```

---

# Platform Goals

VORTEX is part of a broader security engineering project focused on:

- distributed systems
- detection pipelines
- observability
- reverse proxy security
- event-driven architecture
- platform engineering

The long-term goal is to evolve VORTEX into a distributed scanning and threat analysis subsystem inside Sentinel OS.

---

# Future Improvements

- Distributed worker nodes
- Real-time event streaming
- File hashing and reputation analysis
- YARA rule support
- Behavioral analysis
- Queue-backed job orchestration
- Threat intelligence integration
- WebSocket/SSE live scan feeds
- Kubernetes deployment
- Multi-node scan scheduling

---

# Status

Active development.