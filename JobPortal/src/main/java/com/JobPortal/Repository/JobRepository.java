package com.JobPortal.Repository;

import com.JobPortal.Model.Job;
import com.JobPortal.Model.JobCategory;
import com.JobPortal.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job,Long> {
    List<Job> findByStatusIn(List<String> status);

    List<Job> findByEmployerAndStatusIn(User employer, List<String> status);

    List<Job> findByCategoryAndStatusIn(JobCategory jobCategory, List<String> status);

    List<Job> findByTitleContainingIgnoreCaseAndStatusIn(String title, List<String> status);

//    List<Job> findByCategoryAndJobTypeAndSalaryRange(JobCategory jobCategory, String jobType);

}
