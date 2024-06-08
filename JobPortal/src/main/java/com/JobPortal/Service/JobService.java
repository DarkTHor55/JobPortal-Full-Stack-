package com.JobPortal.Service;

import com.JobPortal.Model.Address;
import com.JobPortal.Model.Job;
import com.JobPortal.Model.JobCategory;
import com.JobPortal.Model.User;

import java.util.List;

public interface JobService {
    Job add(Job job, Address address);

    Job update(Job job, Address address);

    Job getById(Long jobId);

    List<Job> getAllByStatus(List<String> status);

    List<Job> getByEmployerAndStatus(User employer, List<String> status);
    List<Job> updateAll(List<Job> jobs);
    List<Job> getAllByCategoryAndStatusIn(JobCategory category, List<String> status);
    List<Job> searchJobNameAndStatusIn(String jobName, List<String> status);
    List<Job> searchJobByCategoryAndTypeAndSalaryRangeAndStatus(JobCategory category, String type, String salaryRange,List<String> status);
}
