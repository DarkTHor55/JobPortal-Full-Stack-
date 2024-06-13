package com.JobPortal.Controller;

import com.JobPortal.Execption.EmailValidationExecption;
import com.JobPortal.Execption.OtpValidtionExection;
import com.JobPortal.Model.Admin;
import com.JobPortal.Repository.AdminRepository;
import com.JobPortal.Request.AdminLoginRequest;
import com.JobPortal.Request.AdminReqest;
import com.JobPortal.Service.AdminService;
import com.JobPortal.ServiceImpl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private AdminRepository adminRepository;
    @PostMapping("/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody AdminReqest admin) throws Exception {
        System.out.println(admin.getEmail());
        Admin adm=adminService.createAdmin(admin);
        return new ResponseEntity<>(adm, HttpStatus.OK);
    }
    @PostMapping("/request-otp")
    public boolean reqValidation(@RequestBody String email) throws OtpValidtionExection, EmailValidationExecption {
        System.out.println(email+"++++++++++++++++++++++++++++++++++++++++++++++++");
        List<Admin> adminList=adminRepository.findAll();
        for (Admin admin : adminList){
            if (admin.getEmail().equals(email)){
                return false;
            }
        }
        boolean test= adminService.emailSend(email);
        if (test){
            return adminService.sendOtp(email);
        }
        return false;

    }
    @PostMapping("/verify-otp")
    public boolean validationOtp(@RequestBody int otp) throws OtpValidtionExection, EmailValidationExecption {
        System.out.println(otp+"y contrller mai h ");
        boolean test= adminService.verifyOtp(otp);
        if (test){
            return true;
        }
        return false;

    }

    @PostMapping("/login")
    public ResponseEntity<Admin> loginAdmin(@RequestBody AdminLoginRequest admin) throws Exception {
        if(admin==null||admin.getEmail().isEmpty()||admin.getPassword().isEmpty()||!admin.getRole().equals("ADMIN")){

            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        System.out.println(admin.getEmail());
        Admin adm=adminService.loginAdmin(admin);
        if(adm==null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(adm, HttpStatus.OK);
    }
}
