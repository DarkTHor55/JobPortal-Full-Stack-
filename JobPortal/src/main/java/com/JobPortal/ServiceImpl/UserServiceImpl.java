package com.JobPortal.ServiceImpl;

import com.JobPortal.Execption.EmailValidationExecption;
import com.JobPortal.Execption.OtpValidtionExection;
import com.JobPortal.Execption.PasswordValidationExecption;
import com.JobPortal.Model.User;
import com.JobPortal.Repository.UserRepository;
import com.JobPortal.Service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailSenderService emailSenderService;
    public static int otp = randomNumber();
    private boolean emailValidator=false;
    private String finalEmail;


    @Override
    public User addUser(User user) throws EmailValidationExecption, OtpValidtionExection {

        //email validation
        if (!emailValidator){
            throw new OtpValidtionExection("OTP is not valid");
        }
        if(isEmailExits(user.getEmail())){
            throw new EmailValidationExecption("Email already exists");
        }

        System.out.println(finalEmail);

        char newEmail[] = new char[finalEmail.length() - 2];

        for (int i = 1; i < finalEmail.length() - 1; i++) {
            newEmail[i - 1] = finalEmail.charAt(i);
        }
        String newEmailString = new String(newEmail);
        System.out.println(newEmailString);
        user.setEmail(newEmailString);

        finalEmail=null;

        //Password Encrytption
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Phone number validation
        Long phoneNumber = user.getPhoneNumber();
        String phoneNumberStr = Long.toString(phoneNumber);
        if (phoneNumberStr.length() != 10) {
            if (phoneNumber < 1000000000L || phoneNumber > 9999999999L) {

                throw new IllegalArgumentException("Phone number must be between 1000000000 and 9999999999");
            }
            throw new IllegalArgumentException("Phone number must be of 10 digit");
        }
        // Role validation
        String userRole = user.getRole();
        if (userRole == null) {
            user.setRole("USER");
        }

        //Registration date Validation
        String registrationDate = user.getRegistrationDate();
        if (registrationDate == null) {
            user.setRegistrationDate(new Date().toString());
        }

        //Status Validation
        String status = user.getStatus();
        if (status == null) {
            user.setStatus("ACTIVE");
        }
        return userRepository.save(user);
    }

    private boolean isEmailExits(String email) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public static int randomNumber() {
        Random random = new Random();
        int min = 1000;
        int max = 9999;
        return random.nextInt((max - min) + 1) + min;
    }

    @Override
    public User     updateUser(String Email, User user) throws OtpValidtionExection, EmailValidationExecption, PasswordValidationExecption {
        User oldUser = userRepository.findByEmail(Email);
        // Email validation
        boolean emailAlreadyExists = false;
        boolean validEmail = false;
        if (isEmailExits(user.getEmail())) {
            emailAlreadyExists = true;
        }
        System.out.println(isEmailExits(user.getEmail()));
        String email = user.getEmail();
        if (!emailAlreadyExists) {
            int countDot = 0;
            for (int i = 0; i < email.length() - 3; i++) {
                if (email.charAt(i) == '.') {
                    System.out.println(i + "................................................................" + email.length());
                    if (email.charAt(i + 1) == 'c' && email.charAt(i + 2) == 'o' && email.charAt(i + 3) == 'm' && email.length() == i + 4) {
                        System.out.println(email.charAt(i + 1) + "........." + email.charAt(i + 2) + "........................." + email.charAt(i + 3) + "..............................");
                        validEmail = true;
                        break;
                    }
                    if (countDot >= 2 || email.charAt(i) == '-' || email.charAt(i) == '!' || email.charAt(i) == '%' ||
                            email.charAt(i) == '^' || email.charAt(i) == '*' || email.charAt(i) == '&' || email.charAt(i) == '(' ||
                            email.charAt(i) == ')' || email.charAt(i) == '[' || email.charAt(i) == '_') {
                        throw new IllegalArgumentException("Invalid email domain in password");
                    }
                }
            }
            if (!validEmail) {
                throw new IllegalArgumentException("Email not valid ");
            }
            String subject = "Your OTP";
            String body = "Your OTP is: " + otp + ". Don't share your OTP with anyone.";
            emailSenderService.sendEmail(email, subject, body, otp);
            Scanner scanner = new Scanner(System.in);
            int checkOtp = scanner.nextInt();
            if (checkOtp != otp) {
                throw new OtpValidtionExection("OTP is not valid");
            } else {
                oldUser.setEmail(user.getEmail());
            }
        } else {
            user.setEmail(oldUser.getEmail());
        }
        //Password Validation
        // Password Sould not be change by updating for changing pass we have to create a diffrerent method because of security issues
//        if (passwordEncoder.matches(user.getPassword(), oldUser.getPassword())) {
//
//            user.setPassword(oldUser.getPassword()); // no need
//        } else {
//            throw new PasswordValidationExecption(" Password Mismatch ");
//        }
        //number
        // Phone number validation
        Long phoneNumber = user.getPhoneNumber();
        String phoneNumberStr = Long.toString(phoneNumber);
        if (phoneNumberStr.length() != 10) {
            if (phoneNumber < 1000000000L && phoneNumber > 9999999999L) {
                oldUser.setPhoneNumber(user.getPhoneNumber());
            } else {
                throw new IllegalArgumentException("Phone number must be between 1000000000 and 9999999999");

            }

            throw new IllegalArgumentException("Phone number must be of 10 digit");
        }
        // Role validation
        // role Not be changed directly due to security issues
        if (!user.getRole().equals(oldUser.getRole())) {
            throw new IllegalArgumentException("Role Not be changed directly due to security issues");
        }
        //Status Validation
        String status = user.getStatus();
        if (status == null) {
            oldUser.setStatus("ACTIVE");
        }
        user.setStatus(status);
        user.setAddress(oldUser.getAddress());
        user.setProfile(oldUser.getProfile());
        user.setRegistrationDate(oldUser.getRegistrationDate());
        userRepository.deleteById(oldUser.getId());
        return userRepository.save(user);
    }

    @Override
    public boolean removeUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(null);

        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public User getUserByEmailAndStatus(String emailId, String status) {
        return userRepository.findByEmailAndStatus(emailId, status);
    }

    @Override
    public User getUserByEmailid(String emailId) {
        return userRepository.findByEmail(emailId);
    }

    @Override
    public List<User> getUserByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public User getUserById(Long userId) {

        User user = this.userRepository.findById(userId).orElseThrow();

        return user;

    }

    @Override
    public User getUserByEmailIdAndRoleAndStatus(String emailId, String role, String status) {
        return this.userRepository.findByEmailAndRoleAndStatus(emailId, role, status);
    }

    @Override
    public List<User> getUserByRoleAndStatus(String role, String status) {
        return this.userRepository.findByRoleAndStatus(role, status);
    }

    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public boolean emailSend(String email) throws OtpValidtionExection, EmailValidationExecption {
        User user = userRepository.findByEmail(email);
        // Email validation
        boolean emailAlreadyExists = false;
        if( user!=null){
            throw new IllegalStateException("User  exist");
        }

        System.out.println(email+"/////////////////");
        char newEmail[] = new char[email.length() - 2];

        for (int i = 1; i < email.length() - 1; i++) {
            newEmail[i - 1] = email.charAt(i);
        }
        String newEmailString = new String(newEmail);
        System.out.println(newEmailString);
        List<User> users = this.userRepository.findAll();
        for (User u : users) {
            if(u.getEmail().equals(newEmailString)){
                emailAlreadyExists=true;
            }
        }
        if (!emailAlreadyExists) {
            int countDot = 0;
            for (int i = 0; i < email.length() - 3; i++) {
                if (email.charAt(i) == '.') {
                    System.out.println(i + "................................................................" + email);
                    if (email.charAt(i + 1) == 'c' && email.charAt(i + 2) == 'o' && email.charAt(i + 3) == 'm' && email.length() == i + 5) {
                        System.out.println(email.charAt(i + 1) + "........." + email.charAt(i + 2) + "........................." + email.charAt(i + 3) + "..............................");
                        finalEmail=email;
                        return true;
                    }
                    if (countDot >= 2 || email.charAt(i) == '-' || email.charAt(i) == '!' || email.charAt(i) == '%' ||
                            email.charAt(i) == '^' || email.charAt(i) == '*' || email.charAt(i) == '&' || email.charAt(i) == '(' ||
                            email.charAt(i) == ')' || email.charAt(i) == '[' || email.charAt(i) == '_') {
                        throw new IllegalArgumentException("Invalid email domain in password");
                    }
                }
            }

        } else {
            throw new EmailValidationExecption("Email already exists");
        }
        return false;

    }
    public  boolean sendOtp (String email) throws OtpValidtionExection {
        String subject = "Your OTP";
        String body = "Your OTP is: " + otp + ". Don't share your OTP with anyone.";
        emailSenderService.sendEmail(email, subject, body, otp);
        return true;

    }

    public boolean verifyOtp(int newOtp) {
        System.out.println(newOtp);
        if(otp==newOtp){
            emailValidator=true;
            return true;
        }
        return false;
    }

}
