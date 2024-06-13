package com.JobPortal.ServiceImpl;

import com.JobPortal.Execption.EmailValidationExecption;
import com.JobPortal.Execption.OtpValidtionExection;
import com.JobPortal.Model.Admin;
import com.JobPortal.Repository.AdminRepository;
import com.JobPortal.Request.AdminLoginRequest;
import com.JobPortal.Request.AdminReqest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements com.JobPortal.Service.AdminService {
    private boolean isAdminVerified = false;
    private boolean isEmailVarified=false;
    private String finalEmail;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private AdminRepository adminRepository;
    private static int otp = UserServiceImpl.otp;

    private boolean isEmailExits(String email) {
        List<Admin> users = adminRepository.findAll();
        for (Admin user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public Admin createAdmin(AdminReqest ad) throws Exception {
        Admin admin=new Admin();
        String email=ad.getEmail();
        if(email==null||email.isEmpty()){
            throw new Exception("Data not found");
        }

        char newEmail[] = new char[email.length() - 2];

        for (int i = 1; i < email.length() - 1; i++) {
            newEmail[i - 1] = email.charAt(i);
        }
        String newEmailString = new String(newEmail);
        admin.setEmail(newEmailString);
        admin.setFirstName(ad.getFirstName());
        admin.setLastName(ad.getLastName());
        admin.setPassword(ad.getPassword());
        if(admin==null){
            throw new Exception("Data not found");
        }
        if(admin.getEmail().isEmpty()||admin.getPassword().isEmpty()){
            throw new Exception("Data not found");

        }
        admin.setCreateAt(new Date());
        admin.setRole("ADMIN");
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setUsername(admin.getFirstName()+""+admin.getLastName()+""+userService.randomNumber());
        String username=admin.getUsername();


        if (isEmailVarified){
            return adminRepository.save(admin);
        }else {
            return null;
        }

}

    @Override
    public Admin updateAdmin(Admin admin) {
        return null;
    }
    public boolean emailSend(String email) throws OtpValidtionExection, EmailValidationExecption {
        if(email.isEmpty()){
            throw new EmailValidationExecption("Email Is Empty");
        }
        boolean isEmailValid=false;

        char newEmail[] = new char[email.length() - 2];

        for (int i = 1; i < email.length() - 1; i++) {
            newEmail[i - 1] = email.charAt(i);
        }
        String newEmailString = new String(newEmail);
        List<Admin>allAdmin=adminRepository.findAll();
        for(Admin admin : allAdmin){
            if (admin.getEmail().equals(newEmailString)){
                isEmailValid=true;
            }
        }
        if (!isEmailValid){
            int countDot = 0;
            for (int i = 0; i < email.length() - 3; i++) {
                if (email.charAt(i) == '.') {
                    if (email.charAt(i + 1) == 'c' && email.charAt(i + 2) == 'o' && email.charAt(i + 3) == 'm' && email.length() == i + 5) {
//                        System.out.println(email.charAt(i + 1) + "........." + email.charAt(i + 2) + "........................." + email.charAt(i + 3) + "..............................");
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

        }


        return false;
    }
    public  boolean sendOtp (String email) throws OtpValidtionExection {
        String subject = "Your OTP";
        String body = "Your OTP is: " + otp + ". Don't share your OTP with anyone.";
        System.out.println(otp);
        emailSenderService.sendEmail(email, subject, body, otp);
        return true;

    }

    public boolean verifyOtp(int newOtp) {
        if(otp==newOtp){
            isEmailVarified = true;
            return true;
        }
        return false;

    }

    public Admin loginAdmin(AdminLoginRequest admin) {
        Admin admin1 = adminRepository.findByEmail(admin.getEmail());
        if (admin1 == null) {
            return null;
        }
        if (!passwordEncoder.matches(admin.getPassword(), admin1.getPassword())) {
            return null;
        }
        if(!admin1.getRole().equals(admin.getRole())){
            return null;
        }
        return admin1;
    }
}
