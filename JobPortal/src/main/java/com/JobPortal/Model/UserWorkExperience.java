package com.JobPortal.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_work_experience")
public class UserWorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double experience;
    private String jobPosition;
    private String company;
    private String startDate;
    private String endDate;
    @Transient
    private Long userId;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userProfile_id")
    private UserProfile userProfile;

}