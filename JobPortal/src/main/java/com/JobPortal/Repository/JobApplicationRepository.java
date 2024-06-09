package com.JobPortal.Repository;

import com.JobPortal.Model.JobApplication;
import com.JobPortal.Model.User;
import com.JobPortal.Response.JobApplicationResponse;
import com.JobPortal.Response.JobResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication ,Long> {
    @Query("SELECT ja FROM JobApplication ja WHERE ja.job.employer = :employer and status In (:status)")
    List<JobApplication> findByEmployerAndStatus(@Param("employer") User employer,
                                                 @Param("status") List<String> status);


//
}
