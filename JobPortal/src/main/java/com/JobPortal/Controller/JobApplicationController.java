package com.JobPortal.Controller;

import com.JobPortal.AllResources.JobApplicationResource;
import com.JobPortal.Execption.JobApplicationSaveException;
import com.JobPortal.Model.JobApplication;

import com.JobPortal.Response.JobApplicationResponse;
import com.JobPortal.Response.JobResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/job/application")
public class JobApplicationController {
    @Autowired
    private JobApplicationResource jobApplicationResource;

    @PostMapping("/add")
    public ResponseEntity<JobResponse> register(@RequestBody JobApplication request) throws JobApplicationSaveException {
        return jobApplicationResource.addJobApplication(request);
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<JobApplicationResponse> fetchAllJobApplications() {
        return jobApplicationResource.fetchAllJobApplications();
    }

    @GetMapping("/fetch/all/employee")
    public ResponseEntity<JobApplicationResponse> fetchAllJobApplicationsByEmployee(
            @RequestParam("employeeId") Long employeeId) {
        return jobApplicationResource.fetchAllJobApplicationsByEmployee(employeeId);
    }

    @GetMapping("/fetch/all/employer")
    public ResponseEntity<JobApplicationResponse> fetchAllJobApplicationsByEmployer(
            @RequestParam("employerId") Long employerId) {
        return jobApplicationResource.fetchAllJobApplicationsByEmployer(employerId);
    }

    @GetMapping("/fetch/all/job-wise")
    public ResponseEntity<JobApplicationResponse> fetchAllJobApplicationsByJob(
            @RequestParam("jobId") Long jobId) {
        return jobApplicationResource.fetchAllJobApplicationsByJob(jobId);
    }

    @GetMapping("/fetch/status-wise/employer")
    public ResponseEntity<JobApplicationResponse> fetchAllJobApplicationsByEmployerAndStatus(
            @RequestParam("employerId") Long employerId, @RequestParam("status") String status) {
        return jobApplicationResource.fetchAllJobApplicationsByEmployerAndStatus(employerId, status);
    }

    @PutMapping("/update/status")
    public ResponseEntity<JobResponse> fetchAllJobApplicationsByEmployee(@RequestBody JobApplication request) throws JobApplicationSaveException {
        return jobApplicationResource.updateJobApplicationStatus(request);
    }

}
