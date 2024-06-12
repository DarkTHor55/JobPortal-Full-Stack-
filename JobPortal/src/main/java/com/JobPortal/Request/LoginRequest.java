package com.JobPortal.Request;


import lombok.*;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    private String email;

    private String password;

    private String role;

}

