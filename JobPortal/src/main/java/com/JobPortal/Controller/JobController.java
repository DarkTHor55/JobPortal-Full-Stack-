package com.JobPortal.Controller;

import com.JobPortal.AllResources.JobResource;
import com.JobPortal.Configuration.JwtUtils;
import com.JobPortal.Request.JobRequest;
import com.JobPortal.Response.JobResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/job")
public class JobController {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private JobResource jobResource;

    @PostMapping("/register")
    public ResponseEntity<JobResponse> createJob(@RequestHeader("authorization") String jwt, @RequestParam("title") String title, @RequestParam("description") String description, @RequestParam("companyName") String companyName, @RequestParam("companyLogo") MultipartFile companyLogo, @RequestParam("jobType") String jobType, @RequestParam("salaryRange") String salaryRange, @RequestParam("experienceLevel") String experienceLevel, @RequestParam("requiredSkills") String requiredSkills, @RequestParam("street") String street, @RequestParam("city") String city, @RequestParam("pincode") String pincode, @RequestParam("state") String state, @RequestParam("country") String country) {
        JobRequest request = new JobRequest(title, description, companyName, companyLogo, jobType, salaryRange, experienceLevel, requiredSkills, street, city, pincode, state, country);
        jwt = jwt.substring(7);
        String email = jwtUtils.extractUsername(jwt);
        return jobResource.createJob(request, email);
    }

    @PostMapping("/updateJob")
    public ResponseEntity<JobResponse> updateJob(@RequestHeader("authorization") String jwt, @RequestParam("title") String title, @RequestParam("description") String description, @RequestParam("companyName") String companyName, @RequestParam("companyLogo") MultipartFile companyLogo, @RequestParam("jobType") String jobType, @RequestParam("salaryRange") String salaryRange, @RequestParam("experienceLevel") String experienceLevel, @RequestParam("requiredSkills") String requiredSkills, @RequestParam("street") String street, @RequestParam("city") String city, @RequestParam("pincode") String pincode, @RequestParam("state") String state, @RequestParam("country") String country) {
        JobRequest request = new JobRequest(title, description, companyName, companyLogo, jobType, salaryRange, experienceLevel, requiredSkills, street, city, pincode, state, country);
        jwt = jwt.substring(7);
        String email = jwtUtils.extractUsername(jwt);
        return jobResource.updateJob(request,email);
    }
}
