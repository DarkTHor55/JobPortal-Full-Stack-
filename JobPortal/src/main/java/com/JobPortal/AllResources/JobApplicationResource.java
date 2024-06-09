package com.JobPortal.AllResources;

import com.JobPortal.Execption.JobApplicationSaveException;
import com.JobPortal.Model.Address;
import com.JobPortal.Model.Job;
import com.JobPortal.Model.JobApplication;
import com.JobPortal.Model.User;
import com.JobPortal.Response.JobApplicationResponse;
import com.JobPortal.Response.JobResponse;
import com.JobPortal.Service.JobApplicationService;
import com.JobPortal.Service.JobService;
import com.JobPortal.ServiceImpl.JobApplicationServiceImpl;
import com.JobPortal.ServiceImpl.JobServiceImpl;
import com.JobPortal.ServiceImpl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class JobApplicationResource {
    private final Logger LOG = LoggerFactory.getLogger(JobApplicationResource.class);
    @Autowired
    private JobServiceImpl jobService;

    @Autowired
    private JobApplicationServiceImpl jobApplicationService;

    @Autowired
    private UserServiceImpl userService;

    public ResponseEntity<JobResponse> addJobApplication(JobApplication request) throws JobApplicationSaveException {
        LOG.info("Request received for addding job application");
        JobResponse response = new JobResponse();
        if (request == null || request.getJobId() == 0 || request.getEmployeeId() == 0) {
            response.setResponseMessage("missing input");
            response.setSuccess(false);
            return new ResponseEntity<JobResponse>(response, HttpStatus.BAD_REQUEST);
        }
        Job job = jobService.getById(request.getJobId());
        if (job == null) {
            response.setResponseMessage("job not found");
            response.setSuccess(false);
            return new ResponseEntity<JobResponse>(response, HttpStatus.BAD_REQUEST);
        }
        User employee = this.userService.getUserById(request.getEmployeeId());

        if (employee == null) {
            response.setResponseMessage("employee not found");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        request.setEmployee(employee);
        request.setJob(job);
        request.setStatus(Constants.JobApplicationStatus.APPLIED.value());
        request.setApplicationId(ApplicationHelper.generateApplicationId());
        request.setDateTime(
                String.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
        JobApplication savedApplication = jobApplicationService.add(request);
        if (savedApplication == null) {
            throw new JobApplicationSaveException("Failed to add job application");
        }

        int totalJobApplicants = job.getApplicationCount();

        job.setApplicationCount(totalJobApplicants + 1);

        Job updatedJob = jobService.update(job,job.getAddress());

        if (updatedJob == null) {
            throw new JobApplicationSaveException("Failed to add job application");
        }

        response.setResponseMessage("Job Applied Successful!!!");
        response.setSuccess(true);

        return new ResponseEntity<JobResponse>(response, HttpStatus.OK);
    }

    public ResponseEntity<JobApplicationResponse> fetchAllJobApplications() {
        LOG.info("Request received for fetching all job applications");
        JobApplicationResponse response = new JobApplicationResponse();

        List<JobApplication> applications = new ArrayList<>();

        applications = this.jobApplicationService.getAll();
        if (applications.isEmpty()) {
            response.setResponseMessage("No Job Applications not found!!");
            response.setSuccess(false);
            return new ResponseEntity<JobApplicationResponse>(response, HttpStatus.OK);
        } else {
            response.setApplications(applications);
            response.setResponseMessage("Job Application Fetched Successful");
            response.setSuccess(true);

            return new ResponseEntity<JobApplicationResponse>(response, HttpStatus.OK);
        }
    }

    public ResponseEntity<JobApplicationResponse> fetchAllJobApplicationsByEmployee(Long employeeId) {
        LOG.info("Request received for fetching all job applications by employee id");
        JobApplicationResponse response = new JobApplicationResponse();
        if (employeeId == 0) {
            response.setResponseMessage("employee not found");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        User employee = userService.getUserById(employeeId);
        if (employee == null) {
            response.setResponseMessage("employee not found");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        List<JobApplication> applications = jobApplicationService.getByEmployee(employee);
        if (applications.isEmpty()) {
            response.setResponseMessage("No Job Applications not found!!");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setApplications(applications);
        response.setResponseMessage("Job Application Fetched Successful");
        response.setSuccess(true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<JobApplicationResponse> fetchAllJobApplicationsByEmployer(Long employerId) {
        LOG.info("Request received for fetching all job applications by employer id");
        JobApplicationResponse response = new JobApplicationResponse();
        if (employerId == 0) {
            response.setResponseMessage("employer not found");
            response.setSuccess(false);
            return new ResponseEntity<JobApplicationResponse>(response, HttpStatus.BAD_REQUEST);
        }
        User employer = userService.getUserById(employerId);
        if (employer == null) {
            response.setResponseMessage("employer not found");
            response.setSuccess(false);
            return new ResponseEntity<JobApplicationResponse>(response, HttpStatus.BAD_REQUEST);
        }
        List<JobApplication> jobApplications =jobApplicationService.getByEmployee(employer);

        if (jobApplications.isEmpty()) {
            response.setResponseMessage("No Job Applications not found!!");
            response.setSuccess(false);

            return new ResponseEntity<JobApplicationResponse>(response, HttpStatus.OK);
        }

        response.setApplications(jobApplications);
        response.setResponseMessage("Job Application Fetched Successful");
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<JobApplicationResponse> fetchAllJobApplicationsByJob(Long jobId) {
        LOG.info("Request received for fetching all job applications by employer id and status");
        JobApplicationResponse response = new JobApplicationResponse();
        if (jobId == 0 ) {
            response.setResponseMessage("Job not found");
            response.setSuccess(false);
            return new ResponseEntity<JobApplicationResponse>(response, HttpStatus.BAD_REQUEST);
        }
        Job job = jobService.getById(jobId);
        if (job==null) {
            response.setResponseMessage("Job not found");
            response.setSuccess(false);
            return new ResponseEntity<JobApplicationResponse>(response, HttpStatus.BAD_REQUEST);
        }
        List<JobApplication> jobApplications = jobApplicationService.getByJob(job);
        if (jobApplications.isEmpty()) {
            response.setResponseMessage("No Job Applications not found!!");
            response.setSuccess(false);
            return new ResponseEntity<JobApplicationResponse>(response, HttpStatus.OK);
        }else {
            response.setApplications(jobApplications);
            response.setResponseMessage("Job Application Fetched Successful");
            response.setSuccess(true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

    public ResponseEntity<JobApplicationResponse> fetchAllJobApplicationsByEmployerAndStatus(Long employerId, String status) {
        LOG.info("Request received for fetching all job applications by employer id and status");

        JobApplicationResponse response = new JobApplicationResponse();

        if (employerId == 0 || status == null) {
            response.setResponseMessage("employer not found");
            response.setSuccess(false);

            return new ResponseEntity<JobApplicationResponse>(response, HttpStatus.BAD_REQUEST);
        }

        User employer = this.userService.getUserById(employerId);

        if (employer == null) {
            response.setResponseMessage("employer not found");
            response.setSuccess(false);

            return new ResponseEntity<JobApplicationResponse>(response, HttpStatus.BAD_REQUEST);
        }

        List<JobApplication> applications = new ArrayList<>();

        applications = jobApplicationService.getByEmployeeAndStatus(employer, Arrays.asList(status));

        if (applications.isEmpty()) {
            response.setResponseMessage("No Job Applications not found!!");
            response.setSuccess(false);

            return new ResponseEntity<JobApplicationResponse>(response, HttpStatus.OK);
        }

        response.setApplications(applications);
        response.setResponseMessage("Job Application Fetched Successful");
        response.setSuccess(true);

        return new ResponseEntity<JobApplicationResponse>(response, HttpStatus.OK);
    }

    public ResponseEntity<JobResponse> updateJobApplicationStatus(JobApplication request) throws JobApplicationSaveException {
        LOG.info("Request received for updating the job application");

        JobResponse response = new JobResponse();

        if (request == null || request.getId() == 0 || request.getStatus() == null) {
            response.setResponseMessage("missing input");
            response.setSuccess(false);

            return new ResponseEntity<JobResponse>(response, HttpStatus.BAD_REQUEST);
        }

        JobApplication jobApplication = this.jobApplicationService.getById(request.getId());

        if (jobApplication == null) {
            response.setResponseMessage("job application not found");
            response.setSuccess(false);

            return new ResponseEntity<JobResponse>(response, HttpStatus.BAD_REQUEST);
        }

        jobApplication.setStatus(request.getStatus());

        JobApplication savedApplication = this.jobApplicationService.update(jobApplication);

        if (savedApplication == null) {
            throw new JobApplicationSaveException("Failed to update the job application status");
        }

        response.setResponseMessage("Job Application Status Updated Successful!!!");
        response.setSuccess(true);

        return new ResponseEntity<JobResponse>(response, HttpStatus.OK);

    }
}
