package com.JobPortal.Controller;

import com.JobPortal.AllResources.UserResources;
import com.JobPortal.Configuration.*;
import com.JobPortal.Execption.EmailValidationExecption;
import com.JobPortal.Execption.OtpValidtionExection;
import com.JobPortal.Execption.PasswordValidationExecption;
import com.JobPortal.Execption.UserSaveFailedException;
import com.JobPortal.Model.*;
import com.JobPortal.Repository.AddressRepository;
import com.JobPortal.Repository.UserRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    private UserResources userResources;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AddressServiceImpl addressService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/verify-otp")
    public boolean otp(@RequestBody Map<String, Object> result) {
        String email = (String) result.get("email");
        int otp = Integer.parseInt(result.get("otp").toString());
        return userService.verifyOtp(otp);
    }
    @PostMapping("/request-otp")
    public boolean validationOtp(@RequestBody String email) throws OtpValidtionExection, EmailValidationExecption {
       boolean test= userService.emailSend(email);
       if (test){
         return userService.sendOtp(email);
       }
       return false;

    }
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest request) throws OtpValidtionExection, EmailValidationExecption {
        return userResources.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request) throws OtpValidtionExection, EmailValidationExecption {
        System.out.println(request.getEmail()+" "+request.getPassword()+" "+request.getRole());
        return userResources.loginUser(request);
    }

//    @DeleteMapping("/delete/{userId}")
//    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
//        return userResources.deleteUser(userId);
//    }

    @GetMapping("/fetch")
    public ResponseEntity<User> fetchUser(@RequestBody Long userId) {
        return userResources.fetchUser(userId);
    }

    @GetMapping("/fetch/role-wise")
    public ResponseEntity<List<User>> fetchUserByRole(@RequestBody String role) {
        return userResources.fetchUserByRole(role);
    }
    @GetMapping("/fetch/all")
    public ResponseEntity<List<User>> fetchAllUser() {
        List<User>users= userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity<ProfileResponse> addUserAddress(@RequestParam("bio") String bio, @RequestParam("website") String website, @RequestParam("linkedlnProfileLink") String linkedlnProfileLink, @RequestParam("githubProfileLink") String githubProfileLink, @RequestParam("resumeLink") MultipartFile resumeLink, @RequestParam("profilePicLink") MultipartFile profilePicLink, @RequestHeader("authorization") String jwt) throws IOException, UserSaveFailedException {
        ProfileRequest profile = new ProfileRequest();
        profile.setBio(bio);
        profile.setWebsite(website);
        profile.setProfilePicLink(profilePicLink);
        profile.setLinkedlnProfileLink(linkedlnProfileLink);
        profile.setGithubProfileLink(githubProfileLink);
        profile.setResumeLink(resumeLink);
        jwt = jwt.substring(7);
        String email = jwtUtils.extractUsername(jwt);
        return userResources.addProfileTOuser(profile, email);
    }

    @PostMapping("/update/skills")
    public ResponseEntity<UserResponse> updateSkill(@RequestBody UserSkill userSkill, @RequestHeader("authorization") String jwt) throws UserSaveFailedException {
        jwt = jwt.substring(7);
        String email = jwtUtils.extractUsername(jwt);
        return userResources.updateSkill(userSkill, email);

    }

    @PostMapping("/update/work-experience")
    public ResponseEntity<UserResponse> updateWorkExp(@RequestBody UserWorkExperience exp, @RequestHeader("authorization") String jwt){
        jwt = jwt.substring(7);
        String email = jwtUtils.extractUsername(jwt);
        return userResources.updateWorkExperience(exp,email);

    }

    @PostMapping("/update/education")
    public ResponseEntity<UserResponse> updateEducation(@RequestBody UserEducation education, @RequestHeader("authorization") String jwt){
        jwt = jwt.substring(7);
        String email = jwtUtils.extractUsername(jwt);
        return userResources.updateEducation(education,email);
    }
    @GetMapping("/resume/download/")
    public ResponseEntity<byte[]> downloadResume(@RequestParam("email") String email){
        return userResources.downloadResume(email);
    }
    @PutMapping("/update/user")
    public ResponseEntity<UserResponse> updateUser(@RequestBody User user, @RequestHeader("authorization") String jwt) throws PasswordValidationExecption, OtpValidtionExection, EmailValidationExecption {
        jwt = jwt.substring(7);
        String email = jwtUtils.extractUsername(jwt);
        return userResources.updateUser(user,email);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId){
        boolean deleted=userService.removeUser(userId);
        if(deleted){
            return new ResponseEntity<>("user Deleted",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/total-users")
    public ResponseEntity<Map<String, Integer>>alluser(){
        List<User> users=userRepository.findAll();
        Map<String, Integer> response = new HashMap<>();
        if(users.size()==0){
            response.put("count",0);
            return  new ResponseEntity<>(response,HttpStatus.OK);
        }
        response.put("count", users.size());
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }




}
