package com.JobPortal.ServiceImpl;

import com.JobPortal.Model.Address;
import com.JobPortal.Model.Job;
import com.JobPortal.Model.JobCategory;
import com.JobPortal.Model.User;
import com.JobPortal.Repository.AddressRepository;
import com.JobPortal.Repository.JobRepository;
import com.JobPortal.Service.JobService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private JobRepository jobRepository;
    public Address findAddressOfJob(Long jobId){
        Job job = jobRepository.findById(jobId).orElseThrow(null);
        return job.getAddress();
    }

    @Override
    public Job add(Job job,Address address) {
        addressRepository.save(address);
        return jobRepository.save(job);
    }

    @Override
    public Job update(Job job,Address address) {
        addressRepository.save(address);
        return jobRepository.save(job);

    }

    @Override
    public Job getById(Long jobId) {
        return jobRepository.findById(jobId).orElseThrow(null);
    }

    @Override
    public List<Job> getAllByStatus(List<String> status) {
        return jobRepository.findByStatusIn(status);
    }

    @Override
    public List<Job> getByEmployerAndStatus(User employer, List<String> status) {
        return  jobRepository.findByEmployerAndStatusIn(employer, status);
    }

    @Override
    public List<Job> updateAll(List<Job> jobs) {
        return jobRepository.saveAll(jobs);
    }

    @Override
    public List<Job> getAllByCategoryAndStatusIn(JobCategory category, List<String> status) {
        return jobRepository.findByCategoryAndStatusIn(category,status);
    }

    @Override
    public List<Job> searchJobNameAndStatusIn(String jobName, List<String> status) {
        return jobRepository.findByTitleContainingIgnoreCaseAndStatusIn(jobName, status);
    }

    @Override
    public List<Job> searchJobByCategoryAndTypeAndSalaryRangeAndStatus(JobCategory category, String type, String salaryRange, List<String> status) {
        return jobRepository.findByCategoryAndJobTypeAndSalaryRangeAndStatusIn(category,type,salaryRange,status);
    }
    public Job findJobByEmailid(String emailid) {
        List<Job> jobs = jobRepository.findAll();
        if (emailid == null || emailid.trim().isEmpty()) {
            System.out.println("Provided emailid is null or empty");
            return null;
        }
        String normalizedEmailid = emailid.trim().toLowerCase();
        for(Job job : jobs) {
            String email = job.getEmployer().getEmail();
            if(email.equals(emailid)){
                return job;
            }
        }
        return null;
    }
}
