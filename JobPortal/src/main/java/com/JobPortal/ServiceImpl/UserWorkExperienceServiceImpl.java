package com.JobPortal.ServiceImpl;

import com.JobPortal.Model.UserWorkExperience;
import com.JobPortal.Repository.UserWorkExerienceRepository;
import com.JobPortal.Service.UserWorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWorkExperienceServiceImpl implements UserWorkExperienceService {
    @Autowired
    private UserWorkExerienceRepository userWorkExerienceRepository;
    @Override
    public UserWorkExperience updateUserWorkExperience(UserWorkExperience userWorkExperience) {
       return userWorkExerienceRepository.save(userWorkExperience);
    }
}
