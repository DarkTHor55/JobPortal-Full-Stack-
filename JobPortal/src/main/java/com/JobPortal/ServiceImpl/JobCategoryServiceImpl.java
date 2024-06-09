package com.JobPortal.ServiceImpl;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.JobPortal.Model.JobCategory;
import com.JobPortal.Repository.JobCategoryRepository;
import com.JobPortal.Service.JobCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class JobCategoryServiceImpl implements JobCategoryService {
    @Autowired
    private JobCategoryRepository jobCategoryRepository;
    @Override
    public JobCategory addJobCategory(JobCategory JobCategory) {
        return jobCategoryRepository.save(JobCategory);
    }

    @Override
    public JobCategory updateJobCategory(JobCategory JobCategory) {
        return jobCategoryRepository.save(JobCategory);
    }

    @Override
    public JobCategory getJobCategoryById(Long JobCategoryId) {
        return jobCategoryRepository.findById(JobCategoryId).orElseThrow(null);
    }

    @Override
    public List<JobCategory> getAllCategories() {
        return jobCategoryRepository.findAll();
    }

    @Override
    public boolean deleteJobCategory(Long jobCategoryId) {
       JobCategory category=jobCategoryRepository.findById(jobCategoryId).orElseThrow(null);
       if (category==null){
           return false;
       }else {
           jobCategoryRepository.deleteById(jobCategoryId);
           return true;
       }
    }
}
