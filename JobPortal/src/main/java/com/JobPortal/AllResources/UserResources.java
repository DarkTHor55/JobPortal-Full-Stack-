package com.JobPortal.AllResources;

import com.JobPortal.Configuration.JwtUtils;
import com.JobPortal.Execption.EmailValidationExecption;
import com.JobPortal.Execption.OtpValidtionExection;
import com.JobPortal.Execption.UserSaveFailedException;
import com.JobPortal.Model.Address;
import com.JobPortal.Model.User;
import com.JobPortal.Model.UserProfile;
import com.JobPortal.Model.UserSkill;
import com.JobPortal.Request.LoginRequest;
import com.JobPortal.Request.ProfileRequest;
import com.JobPortal.Request.UserRequest;
import com.JobPortal.Response.LoginResponse;
import com.JobPortal.Response.ProfileResponse;
import com.JobPortal.Response.UserResponse;
import com.JobPortal.ServiceImpl.AddressServiceImpl;
import com.JobPortal.ServiceImpl.UserProfileServiceImpl;
import com.JobPortal.ServiceImpl.UserServiceImpl;
import com.JobPortal.ServiceImpl.UserSkillsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class UserResources {
    private final Logger LOG = LoggerFactory.getLogger(UserResponse.class);
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private AddressServiceImpl addressService;
    @Autowired
    private UserSkillsServiceImpl userSkillsService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserProfileServiceImpl userProfileService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<UserResponse> registerUser(UserRequest request) throws OtpValidtionExection, EmailValidationExecption {

        LOG.info("Received request for register user");

        UserResponse response = new UserResponse();
//if we gave nullresponse
        if (request == null) {
            response.setResponseMessage("user is null");
            response.setSuccess(false);
            return new ResponseEntity<UserResponse>(response, HttpStatus.BAD_REQUEST);
        }

        User existingUser = userService.getUserByEmailAndStatus(request.getEmail(), Constants.ActiveStatus.ACTIVE.value());
//if  that user already exits
        if (existingUser != null) {
            response.setResponseMessage("User with this Email Id already resgistered!!!");
            response.setSuccess(false);
            return new ResponseEntity<UserResponse>(response, HttpStatus.BAD_REQUEST);
        }
//if role is null
        if (request.getRole() == null) {
            response.setResponseMessage("bad request ,Role is missing");
            response.setSuccess(false);

            return new ResponseEntity<UserResponse>(response, HttpStatus.BAD_REQUEST);
        }

        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPincode(request.getPincode());
        address.setCountry(request.getCountry());
        Address addResponse = addressService.addUserAddress(address);
        User user = new User();
        user.setAddress(address);
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(Constants.UserRole.ROLE_ADMIN.value());
        user.setRegistrationDate(new Date().toString());
        user.setStatus("ACTIVE");
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        UserProfile newProfile = userProfileService.add(new UserProfile());
        user.setProfile(newProfile);

        User userResponse = userService.addUser(user);


        UserResponse res = new UserResponse();
        boolean one = false, two = false;
        if (userResponse == null) {
            response.setResponseMessage("User not Added");
            one = true;
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
        if (addResponse == null) {
            response.setResponseMessage("Address not Added");
            two = true;
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (one || two) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new UserResponse("User and Address added", true), HttpStatus.OK);
        }
    }

    public ResponseEntity<LoginResponse> loginUser(LoginRequest request) {
        LOG.info("Received request for User Login");
        LoginResponse response = new LoginResponse();
//        check request is not empty
//        here we use inheritance by extending

        if (request == null) {
            response.setResponseMessage("Missing Input");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        User us = userService.login(request.getEmailId(), request.getPassword());
        if (us == null) {
            response.setResponseMessage("Invalid input");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        String jwtToken = null;
        User user = null;

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(request.getRole()));
        Authentication authentication;
        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmailId(), request.getPassword(), authorities));
            authentication = new UsernamePasswordAuthenticationToken(request.getEmailId(), request.getPassword(), authorities);
        } catch (Exception ex) {
            response.setResponseMessage("Invalid email or password.");
            response.setSuccess(false);
            return new ResponseEntity<LoginResponse>(response, HttpStatus.BAD_REQUEST);
        }
//        jwtToken = jwtUtils.generateToken(authentication.getPrincipal().toString())
        jwtToken = jwtUtils.generateToken(authentication);
        LOG.info(jwtToken);

//        user = this.userService.getUserByEmailIdAndRoleAndStatus(request.getEmailId(), request.getRole(),
//                Constants.ActiveStatus.ACTIVE.value());
        user = userService.getUserByEmailid(request.getEmailId());
        System.out.println(user.getEmail());
        if (jwtToken != null) {
            response.setUser(user);
            response.setResponseMessage("Logged in sucessful");
            response.setSuccess(true);
            response.setJwtToken(jwtToken);
            return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
        } else {
            response.setResponseMessage("Failed to login");
            response.setSuccess(false);
            return new ResponseEntity<LoginResponse>(response, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> deleteUser(Long userId) {
        boolean success = userService.removeUser(userId);
        if (success) {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found in database", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<User> fetchUser(Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<List<User>> fetchUserByRole(String role) {
        List<User> users = userService.getUserByRole(role);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public ResponseEntity<ProfileResponse> addProfileTOuser(ProfileRequest profile, String email) throws IOException, UserSaveFailedException {
        ProfileResponse response = new ProfileResponse();
        User user = userService.getUserByEmailid(email);
        if (profile == null || email == null || profile.getResumeLink() == null
                || profile.getProfilePicLink() == null) {
            response.setSuccess(false);
            response.setResponse("Data not satisfied !!!  Please enter all  and correct details");
            response.setUser(user);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (user == null) {
            response.setResponse("User not found");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        UserProfile preProfile = user.getProfile();
        preProfile.setId(preProfile.getId());
        preProfile.setBio(profile.getBio());
        preProfile.setWebsite(profile.getWebsite());
        preProfile.setLinkedlnProfileLink(profile.getLinkedlnProfileLink());
        if (profile.getProfilePicLink() == null) {
            preProfile.setProfilePic(defaultPic());
        } else {
            preProfile.setProfilePic(convertImageToByteArray(profile.getProfilePicLink()));
        }
        if (profile.getResumeLink() == null) {
            preProfile.setResume(null);
        } else {
            preProfile.setResume(convertFileToByteArray(profile.getResumeLink()));
        }
        preProfile.setGithubProfileLink(profile.getGithubProfileLink());
        preProfile.setSkills(null);
        preProfile.setEducations(null);
        preProfile.setWorkExperiences(null);
        UserProfile profile1 = userProfileService.update(preProfile);
        if (profile1 == null) {
            throw new UserSaveFailedException("Failed to update the User Profile");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<UserResponse> updateSkill(UserSkill userSkill, String email) throws UserSaveFailedException {
        User user = userService.getUserByEmailid(email);
        UserProfile userProfile = user.getProfile();
        UserResponse response = new UserResponse();
        if (user == null) {
            response.setResponseMessage("User Not found");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (userSkill.getSkill() == null || userSkill.getExperience() <= -1) {
            response.setResponseMessage("Input are not valid");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (userSkill == null) {
            response.setResponseMessage("Empty Skill is not valid");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (userProfile == null) {
            response.setResponseMessage("No user FOund whoch this email");
            response.setSuccess(false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        userSkill.setUserProfile(userProfile);
        UserSkill userSkill1 = userSkillsService.updateUserSkill(userSkill,userProfile);
        if (userSkill1 == null) {
            throw new UserSaveFailedException("Failed to update the User Skill");
        } else {
            response.setSuccess(true);
            response.setResponseMessage("Skill updated successfully");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);


    }


    public static byte[] convertImageToByteArray(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            System.out.println("Error yha h");
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] convertFileToByteArray(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            System.out.println("Error yha h");
            e.printStackTrace();

            return null;
        }
    }

    public static byte[] defaultPic() {
        String filePath = "src/main/resources/static/darkthor.png";
        Path path = Paths.get(filePath);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}