package com.JobPortal.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bio;
    private String website;
    @Lob
    @Column(length = 1000000)
    private byte[] resume;
    private String linkedlnProfileLink;
    private String githubProfileLink;
    @OneToMany(mappedBy = "userProfile")
    private List<UserSkill> skills;

    @OneToMany(mappedBy = "userProfile")
    private List<UserEducation> educations;

    @OneToMany(mappedBy = "userProfile")
    private List<UserWorkExperience> workExperiences;
    @Lob
    @Column(length = 1000000)
    private byte[] profilePic;


}