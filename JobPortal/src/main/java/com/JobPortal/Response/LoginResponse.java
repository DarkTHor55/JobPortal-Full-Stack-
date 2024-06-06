package com.JobPortal.Response;

import com.JobPortal.Model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse extends UserResponse{
    private User user;

    private String jwtToken;
}
