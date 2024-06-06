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
    @Column(name = "id", nullable = false)
    private Long id;

    private String degree;

    private String institution;

    private String startDate;

    private String endDate;

    @Transient
    private int userId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userProfile_id")
    private UserProfile userProfile;


}