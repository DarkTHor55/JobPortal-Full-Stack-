package com.JobPortal.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_education")
public class UserEducation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String degree;

    private String institution;

    private String startDate;

    private String endDate;

    @Transient
    private Long userId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userProfile_id")
    private UserProfile userProfile;


}