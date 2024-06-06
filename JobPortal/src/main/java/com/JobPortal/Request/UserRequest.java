package com.JobPortal.Request;

import com.JobPortal.Model.Address;
import com.JobPortal.Model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    // user details
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long phoneNumber;
    private String role;

    // user address
    private String street;

    private String city;

    private String pincode;

    private String state;

    private String country;

}
