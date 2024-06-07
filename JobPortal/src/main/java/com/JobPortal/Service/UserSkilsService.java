package com.JobPortal.Service;

import com.JobPortal.Model.UserProfile;
import com.JobPortal.Model.UserSkill;

public interface UserSkilsService {
    UserSkill updateUserSkill(UserSkill userSkill, UserProfile profile);
}
