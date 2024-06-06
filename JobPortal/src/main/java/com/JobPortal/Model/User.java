package com.JobPortal.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"address", "profile"})
@ToString(exclude = {"address", "profile"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    private Long phoneNumber;
    private String role;
    private String registrationDate;
    private String status;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToOne
    @JoinColumn(name = "profile_id")
    private UserProfile profile;
}
