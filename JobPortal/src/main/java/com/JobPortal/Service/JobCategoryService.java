package com.JobPortal.Service;

import com.JobPortal.Model.JobCategory;

import java.util.List;

public interface JobCategoryService {
    JobCategory addJobCategory(JobCategory JobCategory);

    JobCategory updateJobCategory(JobCategory JobCategory);

    JobCategory getJobCategoryById(Long JobCategory);

//    List<JobCategory> getCategoriesByStatusIn(List<String> status);
    List<JobCategory> getAllCategories();
    boolean deleteJobCategory(Long jobCategoryId);

}
