package com.JobPortal.Service;

import com.JobPortal.Model.UserProfile;

public interface UserProfileService {
    UserProfile add(UserProfile userProfile);

    UserProfile update(UserProfile userProfile);

    UserProfile getById(Long userProfileId);
     UserProfile getProfileByuserId(Long userId);

}
