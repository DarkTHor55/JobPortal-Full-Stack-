package com.JobPortal.AllResources;
import com.JobPortal.Model.Address;
import com.JobPortal.Model.Job;
import com.JobPortal.Model.User;
import com.JobPortal.Request.JobRequest;
import com.JobPortal.Response.JobResponse;
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
    private JobServiceImpl jobService;
    @Autowired
    private UserServiceImpl userService;
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
        job.setCategory(null);
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
        job.setCategory(oldJob.getCategory());
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
}
