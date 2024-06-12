package com.JobPortal.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminReqest {
    private String email;
    private String firstName;
    private String lastName;
    private String password;

}
