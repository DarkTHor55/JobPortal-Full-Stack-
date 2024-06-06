package com.JobPortal.ServiceImpl;

import com.JobPortal.Model.User;
import com.JobPortal.Model.UserProfile;
import com.JobPortal.Repository.UserProfileRepository;
import com.JobPortal.Service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserServiceImpl userService;
    @Override
    public UserProfile add(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    @Override
    public UserProfile update(UserProfile userProfile) {

        return userProfileRepository.save(userProfile);
    }


    @Override
    public UserProfile getById(Long userProfileId) {
        return userProfileRepository.findById(userProfileId).orElseThrow(null);
    }

    @Override
    public UserProfile getProfileByuserId(Long userId) {
        User user=userService.getUserById(userId);
        UserProfile profile = user.getProfile();
        return profile;
    }
}
