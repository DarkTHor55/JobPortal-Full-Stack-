package com.JobPortal.Service;

import com.JobPortal.Model.UserProfile;
import com.JobPortal.Model.UserSkill;

public interface UserSkilsService {
    UserSkill updateUserSkill(UserSkill userSkill, UserProfile profile);

    // ek to delete skill ka bnana h jisme y ho ki wo check b kre ki login wale hi person ka krra h wo delete y na ho ki wo kisi ka b krle
    void deleteUserSkill(Long skillId);
}
