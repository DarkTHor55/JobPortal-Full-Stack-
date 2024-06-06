package com.JobPortal.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_skill")
public class UserSkill {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String skill;

    private double experience;

    @Transient
    private int userId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userProfile_id")
    private UserProfile userProfile;


}