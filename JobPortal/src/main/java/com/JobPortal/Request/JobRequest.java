package com.JobPortal.Request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobRequest {

    @NotNull
    private Long jobCategoryId;
    @NotBlank
    private String title;

    @NotBlank
    @Column(length = 100000)
    private String description;

    @NotBlank
    private String companyName;

    @NotBlank
    private MultipartFile companyLogo;

    @NotBlank
    private String jobType;

    @NotBlank
    private String salaryRange;

    @NotBlank
    private String experienceLevel;

    @NotBlank
    private String requiredSkills;

    // address
    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    private String pincode;

    @NotBlank
    private String state;

    @NotBlank
    private String country;
}
