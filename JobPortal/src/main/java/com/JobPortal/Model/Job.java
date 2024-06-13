package com.JobPortal.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "employer_id")
    private User employer;
    private String title;
    @Lob
    @Column(length = 100000)
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private JobCategory category;
    private String companyName;
    @Lob
    @Column(length = 100000)
    private byte[] companyLogo;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    private String jobType;
    private String salaryRange;
    private String experienceLevel;
    private List<String> requiredSkills;
    private String status;
    private Date datePosted;
    private int applicationCount;
}
