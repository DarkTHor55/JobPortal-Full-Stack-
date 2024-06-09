package com.JobPortal.Repository;

import com.JobPortal.Model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication ,Long> {
}
