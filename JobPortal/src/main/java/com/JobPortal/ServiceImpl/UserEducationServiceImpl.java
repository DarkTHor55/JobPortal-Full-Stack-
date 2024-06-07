package com.JobPortal.ServiceImpl;

import com.JobPortal.Model.UserEducation;
import com.JobPortal.Repository.UserEducationRepository;
import com.JobPortal.Service.UserEducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEducationServiceImpl implements UserEducationService {
    @Autowired
    private UserEducationRepository userEducationRepository;
    @Override
    public UserEducation updateUserEducation(UserEducation userEducation) {
        return userEducationRepository.save(userEducation);
    }
}
