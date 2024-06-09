package com.JobPortal.Repository;

import com.JobPortal.Model.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCategoryRepository extends JpaRepository<JobCategory,Long> {
}
