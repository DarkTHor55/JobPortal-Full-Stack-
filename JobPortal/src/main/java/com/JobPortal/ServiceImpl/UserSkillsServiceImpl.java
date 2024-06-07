package com.JobPortal.ServiceImpl;

import com.JobPortal.Model.UserProfile;
import com.JobPortal.Model.UserSkill;
import com.JobPortal.Repository.UserSkillsRepository;
import com.JobPortal.Service.UserSkilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSkillsServiceImpl implements UserSkilsService {
    @Autowired
    private UserSkillsRepository userSkillsRepository;

    @Override
    public UserSkill updateUserSkill(UserSkill userSkill , UserProfile profile) {
//        List<UserSkill> alPreviousUserSkills=userSkillsRepository.findUserSkillsByProfileId(profile.getId());
//        abi isme bhot kam h abi repeat skills ko b handle krna h

        return userSkillsRepository.save(userSkill);
    }
}
