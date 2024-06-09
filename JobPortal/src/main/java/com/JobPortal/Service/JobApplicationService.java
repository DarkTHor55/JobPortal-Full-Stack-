package com.JobPortal.Service;

import com.JobPortal.Model.Job;
import com.JobPortal.Model.JobApplication;
import com.JobPortal.Model.User;

import java.util.List;

public interface JobApplicationService {
    JobApplication add(JobApplication jobApplication);

    JobApplication update(JobApplication jobApplication);

    JobApplication getById(Long jobApplicationId);

    List<JobApplication> getAll();

    List<JobApplication> getByEmployee(User employee);

//    List<JobApplication> getByEmployeeAndStatus(User employee, List<String> status);

    List<JobApplication> getByJob(Job job);
}
