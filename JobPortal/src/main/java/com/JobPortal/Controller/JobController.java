package com.JobPortal.Controller;

import com.JobPortal.AllResources.JobResource;
import com.JobPortal.Configuration.JwtUtils;
import com.JobPortal.Model.Job;
import com.JobPortal.Model.JobCategory;
import com.JobPortal.Model.User;
import com.JobPortal.Repository.JobRepository;
import com.JobPortal.Request.JobRequest;
import com.JobPortal.Response.JobResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.JobName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/job")
public class JobController {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private JobResource jobResource;
    @Autowired
    private JobRepository jobRepository;

//    @PostMapping("/register")
//    public ResponseEntity<JobResponse> createJob(@RequestHeader("authorization") String jwt,@RequestParam("jobCategoryId")Long jobCategoryId, @RequestParam("title") String title, @RequestParam("description") String description, @RequestParam("companyName") String companyName, @RequestParam("companyLogo") MultipartFile companyLogo, @RequestParam("jobType") String jobType, @RequestParam("salaryRange") String salaryRange, @RequestParam("experienceLevel") String experienceLevel, @RequestParam("requiredSkills") String requiredSkills, @RequestParam("street") String street, @RequestParam("city") String city, @RequestParam("pincode") String pincode, @RequestParam("state") String state, @RequestParam("country") String country) {
//        JobRequest request = new JobRequest(jobCategoryId,title, description, companyName, companyLogo, jobType, salaryRange, experienceLevel, requiredSkills, street, city, pincode, state, country);
//        jwt = jwt.substring(7);
//        String email = jwtUtils.extractUsername(jwt);
//        return jobResource.createJob(request, email);
//    }
    @PostMapping("/register")
    public ResponseEntity<JobResponse> createJob(@RequestHeader("authorization") String jwt, @ModelAttribute JobRequest request){
        System.out.println(request.getJobCategoryId()+"iiiiiiiiiiiiiiiidddddddddddddddd");

        jwt = jwt.substring(7);
        String email = jwtUtils.extractUsername(jwt);
        return jobResource.createJob(request, email);
    }
    @PostMapping("/updateJob")
    public ResponseEntity<JobResponse> updateJob(@RequestHeader("authorization") String jwt,@RequestParam("jobCategoryId")Long jobCategoryId, @RequestParam("title") String title, @RequestParam("description") String description, @RequestParam("companyName") String companyName, @RequestParam("companyLogo") MultipartFile companyLogo, @RequestParam("jobType") String jobType, @RequestParam("salaryRange") String salaryRange, @RequestParam("experienceLevel") String experienceLevel, @RequestParam("requiredSkills") String requiredSkills, @RequestParam("street") String street, @RequestParam("city") String city, @RequestParam("pincode") String pincode, @RequestParam("state") String state, @RequestParam("country") String country) {
        JobRequest request = new JobRequest(jobCategoryId,title, description, companyName, companyLogo, jobType, salaryRange, experienceLevel, requiredSkills, street, city, pincode, state, country);
        jwt = jwt.substring(7);
        String email = jwtUtils.extractUsername(jwt);
        return jobResource.updateJob(request,email);
    }
    @GetMapping("/fetch/all")
    public ResponseEntity<List<Job>> fetchAllJob() {
        return jobResource.fetchAllJob();
    }
    @GetMapping("/search")
    public ResponseEntity<JobResponse> searchJobs(@RequestParam("categoryId") Long categoryId,
                                                  @RequestParam("jobType") String type, @RequestParam("salaryRange") String salaryRange) {
        return jobResource.searchJobs(categoryId, type, salaryRange);
    }
    @GetMapping("/fetch")
    public ResponseEntity<Job> searchJobs(@RequestParam("jobId") Long jobId) {
        return jobResource.getJobById(jobId);
    }

    @GetMapping("/Id")
    public ResponseEntity<Job> getJobsById(@RequestParam("employerId") Long employerId) {
        return jobResource.getJobById(employerId);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<JobResponse> deleteJob(@PathVariable("id") Long jobId) {
        return jobResource.deleteJob(jobId);
    }
    @GetMapping("/total-jobs")
    public ResponseEntity<Map<String, Integer>>allJobs(){
        List<Job> jobs=jobRepository.findAll();
        Map<String, Integer> response = new HashMap<>();

        if(jobs.size()==0){
            response.put("count",0);
            return  new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.put("count", jobs.size());
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


}
