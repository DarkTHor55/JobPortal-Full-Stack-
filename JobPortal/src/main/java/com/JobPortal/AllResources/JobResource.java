package com.JobPortal.AllResources;
import com.JobPortal.Model.Address;
import com.JobPortal.Model.Job;
import com.JobPortal.Model.User;
import com.JobPortal.Repository.JobRepository;
import com.JobPortal.Request.JobRequest;
import com.JobPortal.Response.JobResponse;
import com.JobPortal.ServiceImpl.JobCategoryServiceImpl;
import com.JobPortal.ServiceImpl.JobServiceImpl;
import com.JobPortal.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JobResource {
    @Autowired
    private JobCategoryServiceImpl jobCategoryService;
    @Autowired
    private JobServiceImpl jobService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private JobRepository jobRepository;

    public ResponseEntity<JobResponse> createJob(JobRequest request,String email) {
        User user=userService.getUserByEmailid(email);
        JobResponse res = new JobResponse();
        Address address = new Address();
        Job job = new Job();
        if(request==null){
            res.setSuccess(false);
            res.setResponseMessage("Invalid Request");
            return ResponseEntity.status(400).body(res);
        }
        address.setPincode(request.getPincode());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setStreet(request.getStreet());
        address.setCountry(request.getCountry());

        job.setTitle(request.getTitle());
        job.setJobType(request.getJobType());
        job.setSalaryRange(request.getSalaryRange());
        job.setExperienceLevel(request.getExperienceLevel());
        List<String>skills=new ArrayList<>();
        skills.add(request.getRequiredSkills());
        job.setRequiredSkills(skills);
        job.setAddress(address);
        job.setEmployer(user);
        job.setCompanyLogo(multiPartFileToByteArray(request.getCompanyLogo()));
        job.setCompanyName(request.getCompanyName());
        job.setDescription(request.getDescription());
        if(request.getJobCategoryId()==null){
            job.setCategory(null);

        }else{
            job.setCategory(jobCategoryService.getJobCategoryById(request.getJobCategoryId()));
        }
        job.setStatus("Active");
        job.setDatePosted(new Date());
        job.setApplicationCount(0);
        Job jj=jobService.add(job,address);
        if (jj==null) {
            res.setSuccess(false);
            res.setResponseMessage("Invalid Request");
            return ResponseEntity.status(400).body(res);
        }else {
            res.setSuccess(true);
            res.setResponseMessage("Job Created Successfully");
            return ResponseEntity.status(201).body(res);
        }
    }
    public ResponseEntity<JobResponse>updateJob(JobRequest request, String email){
        User user=userService.getUserByEmailid(email);
        JobResponse res = new JobResponse();
        Address address = new Address();

        Job job = new Job();
        if(request==null){
            res.setSuccess(false);
            res.setResponseMessage("Invalid Request");
            return ResponseEntity.status(400).body(res);
        }
        //find older job
        Job oldJob=jobService.findJobByEmailid(email);
        if(oldJob==null){
            res.setResponseMessage("Job Not Found");
            res.setSuccess(false);
            return new ResponseEntity<JobResponse>(res, HttpStatus.NOT_FOUND);
        }
        address.setId(oldJob.getAddress().getId());
        address.setPincode(request.getPincode());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setStreet(request.getStreet());
        address.setCountry(request.getCountry());

        job.setId(oldJob.getId());
        job.setTitle(request.getTitle());
        job.setJobType(request.getJobType());
        job.setSalaryRange(request.getSalaryRange());
        job.setExperienceLevel(request.getExperienceLevel());
        List<String>skills=oldJob.getRequiredSkills();
        skills.add(request.getRequiredSkills());
        job.setRequiredSkills(skills);
        job.setAddress(address);
        job.setEmployer(user);
        job.setCompanyLogo(multiPartFileToByteArray(request.getCompanyLogo()));
        job.setCompanyName(request.getCompanyName());
        job.setDescription(request.getDescription());
        if(request.getJobCategoryId()==null){
            job.setCategory(null);

        }else{
            job.setCategory(jobCategoryService.getJobCategoryById(request.getJobCategoryId()));
        }
        job.setStatus(oldJob.getStatus());
        job.setDatePosted(oldJob.getDatePosted());
        job.setApplicationCount(oldJob.getApplicationCount());
        Job jj=jobService.update(job,address);
        if (jj==null){
            res.setSuccess(false);
            res.setResponseMessage("Invalid Request");
            return ResponseEntity.status(400).body(res);
        }else{
            res.setSuccess(true);
            res.setResponseMessage("Job Updated Successfully");
            return ResponseEntity.status(201).body(res);
        }
    }



    private byte[] multiPartFileToByteArray(MultipartFile file){
        try {
            return file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<List<Job>> fetchAllJob() {
        List<Job> jobs = jobRepository.findAll();
        if (jobs==null){
            return ResponseEntity.status(404).body(null);
        }
        return new ResponseEntity<>(jobs,HttpStatus.OK);
    }

    public ResponseEntity<JobResponse> searchJobs(Long categoryId, String type, String salaryRange) {
        JobResponse res=new JobResponse();
        List<Job> jobs=jobRepository.findAll();
        List<Job> jobsFinl=new ArrayList<>();
        for (Job job : jobs){
            if (job.getCategory().getId()==categoryId&&job.getJobType()==type&& salaryRange==job.getSalaryRange()){
                jobsFinl.add(job);
            }
        }
        if (jobsFinl.size()==0){
            res.setSuccess(false);
            res.setResponseMessage("No Jobs");
            return new ResponseEntity<>(res,HttpStatus.NOT_FOUND);

        }else {
            res.setSuccess(true);
            res.setResponseMessage("Jobs Found");
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
    }

    public ResponseEntity<Job> getJobById(Long jobId) {
        Job job = jobRepository.findById(jobId).orElse(null);
        JobResponse response   =new JobResponse();
        if (job==null){
            response.setSuccess(false);
            response.setResponseMessage("Job Not exists");
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        else {
            response.setResponseMessage("Job found");
            response.setSuccess(true);
            return new ResponseEntity<>(job,HttpStatus.OK);

        }
    }

    public ResponseEntity<List<Job>> getJobByEmployerId(Long employerId) {
        User user=userService.getUserById(employerId);
        JobResponse response=new JobResponse();
        if (user==null){
            response.setSuccess(false);
            response.setResponseMessage("User Not exists");
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        List<Job> jobs=jobRepository.findAll();
        List<Job> jobsFinl=new ArrayList<>();
        for (Job job : jobs){
            if (job.getEmployer().getId()==employerId){
                jobsFinl.add(job);
            }
        }
        if(jobsFinl.size()==0){
            response.setSuccess(false);
            response.setResponseMessage("No Jobs");
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }else {
            response.setSuccess(true);
            response.setResponseMessage("Jobs Found");
            return new ResponseEntity<>(jobsFinl,HttpStatus.OK);
        }
    }

    public ResponseEntity<JobResponse> deleteJob(Long jobId) {
        Job job = jobRepository.findById(jobId).orElse(null);
        JobResponse response   =new JobResponse();
        if (job==null){
            response.setSuccess(false);
            response.setResponseMessage("Job Not exists");
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        jobRepository.delete(job);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
