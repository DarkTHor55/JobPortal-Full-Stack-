package com.JobPortal.Request;

import com.JobPortal.Model.UserEducation;
import com.JobPortal.Model.UserSkill;
import com.JobPortal.Model.UserWorkExperience;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {
    private Long id;
    private String bio;
    private String website;
    private String linkedlnProfileLink;
    private String githubProfileLink;
    private MultipartFile resumeLink;
    private MultipartFile profilePicLink;

}
