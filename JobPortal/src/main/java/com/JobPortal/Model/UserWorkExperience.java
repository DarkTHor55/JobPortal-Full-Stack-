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
    @Column(name = "id", nullable = false)
    private Long id;

    private double experience;

    private String jobPosition;

    private String company;

    private String startDate;

    private String endDate;

    @Transient
    private int userId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userProfile_id")
    private UserProfile userProfile;

}