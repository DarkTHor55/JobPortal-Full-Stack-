package com.JobPortal.Service;

import com.JobPortal.Execption.EmailValidationExecption;
import com.JobPortal.Execption.OtpValidtionExection;
import com.JobPortal.Execption.PasswordValidationExecption;
import com.JobPortal.Model.User;

import java.util.List;

public interface UserService {

    User addUser(User user) throws EmailValidationExecption, OtpValidtionExection;

    User updateUser(String Email, User user) throws OtpValidtionExection, EmailValidationExecption, PasswordValidationExecption;

    boolean removeUser(Long userId);

    User getUserByEmailAndStatus(String emailId, String status);

    User getUserByEmailid(String emailId);

    List<User> getUserByRole(String role);

    User getUserById(Long userId);

    User getUserByEmailIdAndRoleAndStatus(String emailId, String role, String status);

    List<User> getUserByRoleAndStatus(String role, String status);

    User login(String email, String password);


}
