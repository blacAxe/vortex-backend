# Vortex Job Service

Simple Spring Boot backend that handles async job processing.

It exposes a small API where you can submit a job, store it in a database, and process it in the background without blocking the request.

---

## What this project does

* Accepts job submissions through an API
* Saves jobs to an in memory database
* Processes jobs asynchronously
* Updates job status from pending to processing to completed

---

## Tech stack

* Java 23
* Spring Boot
* Spring Web
* Spring Data JPA
* H2 Database
* Async processing with CompletableFuture

---

## API

### Submit a job

POST /api/jobs/submit

Example body

{
"name": "My Job"
}

Returns the created job with its id and status

---

### Get all jobs

GET /api/jobs/all

Returns a list of all jobs with their current status

---

## How it works

* A job is created with status pending
* The worker service picks it up asynchronously
* Status is updated to processing
* After a delay the job is marked completed
* All updates are stored in the database

---

## Run locally

1. Clone the repo
2. Run the application

mvn spring-boot:run

3. Test with curl or PowerShell

---

## Example

Submit a job

Invoke-RestMethod -Uri http://localhost:8080/api/jobs/submit -Method Post -Body '{"name":"Test"}' -ContentType "application/json"

Get all jobs

Invoke-RestMethod -Uri http://localhost:8080/api/jobs/all -Method Get

---

## Notes

This project is a simple foundation for building background processing systems like video pipelines or task queues.

Future improvements could include

* Persistent database
* Queue system
* Retry logic
* Job priority
* Authentication

---
