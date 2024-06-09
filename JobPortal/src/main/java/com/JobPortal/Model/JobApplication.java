package com.JobPortal.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String applicationId;
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;
    private String dateTime;
    private String status; // Applied, Shortlisted, Rejected
    @Transient
    private Long jobId;
    @Transient
    private Long employeeId;


}
