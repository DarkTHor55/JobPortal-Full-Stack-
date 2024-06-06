package com.JobPortal.Controller;

import com.JobPortal.AllResources.UserResources;
import com.JobPortal.Configuration.JwtUtils;
import com.JobPortal.Execption.EmailValidationExecption;
import com.JobPortal.Execption.OtpValidtionExection;
import com.JobPortal.Execption.UserSaveFailedException;
import com.JobPortal.Model.Address;
import com.JobPortal.Model.User;
import com.JobPortal.Model.UserProfile;
import com.JobPortal.Repository.AddressRepository;
import com.JobPortal.Request.LoginRequest;
import com.JobPortal.Request.ProfileRequest;
import com.JobPortal.Request.UserRequest;
import com.JobPortal.Response.LoginResponse;
import com.JobPortal.Response.ProfileResponse;
import com.JobPortal.Response.UserResponse;
import com.JobPortal.ServiceImpl.AddressServiceImpl;
import com.JobPortal.ServiceImpl.UserServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserResources userResources;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AddressServiceImpl addressService;
    @Autowired
    private UserServiceImpl userService;
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest request) throws OtpValidtionExection, EmailValidationExecption {
        return userResources.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request) throws OtpValidtionExection, EmailValidationExecption {
        return userResources.loginUser(request);
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId)  {
        return userResources.deleteUser(userId);
    }

    @GetMapping("/fetch")
    public ResponseEntity<User>fetchUser(@RequestBody Long userId) {
        return userResources.fetchUser(userId);
    }
    @GetMapping("/fetch/role-wise")
    public ResponseEntity<List<User>>fetchUserByRole(@RequestBody String role) {
        return userResources.fetchUserByRole(role);
    }
    @PostMapping("/profile")
    public ResponseEntity<ProfileResponse> addUserAddress(@RequestBody ProfileRequest profile, @RequestHeader("authorization") String jwt) throws IOException, UserSaveFailedException {
        jwt=jwt.substring(7);
        String email=jwtUtils.extractUsername(jwt);
        return userResources.addProfileTOuser(profile,email);


    }


}