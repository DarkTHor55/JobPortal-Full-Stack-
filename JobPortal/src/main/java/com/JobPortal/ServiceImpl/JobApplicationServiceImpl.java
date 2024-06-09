package com.JobPortal.ServiceImpl;

import com.JobPortal.Model.Job;
import com.JobPortal.Model.JobApplication;
import com.JobPortal.Model.User;
import com.JobPortal.Repository.JobApplicationRepository;
import com.JobPortal.Service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class JobApplicationServiceImpl implements JobApplicationService {
    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    @Override
    public JobApplication add(JobApplication jobApplication) {
        return jobApplicationRepository.save(jobApplication);
    }

    @Override
    public JobApplication update(JobApplication jobApplication) {
        return jobApplicationRepository.save(jobApplication);
    }

    @Override
    public JobApplication getById(Long jobApplicationId) {
        return jobApplicationRepository.findById(jobApplicationId).orElseThrow(null);
    }

    @Override
    public List<JobApplication> getAll() {
        return jobApplicationRepository.findAll();
    }

    @Override
    public List<JobApplication> getByEmployee(User employee) {
        List<JobApplication> list = getAll();
        List<JobApplication>finalList = new ArrayList<>();
        Long userid=employee.getId();
        for (JobApplication job: list) {
            if(job.getEmployee().getId()==userid) {
                finalList.add(job);
            }
        }
        return finalList;
    }
    public List<JobApplication> getByEmployeeAndStatus(User employee, List<String> status) {
        return jobApplicationRepository.findByEmployerAndStatus(employee, status);
    }


    @Override
    public List<JobApplication> getByJob(Job job) {
        List<JobApplication> list = getAll();
        List<JobApplication>finalList = new ArrayList<>();
        for (JobApplication j: list) {
            if(j.getEmployee().getId()==job.getId()) {
                finalList.add(j);
            }
        }
        return finalList;
    }
}
