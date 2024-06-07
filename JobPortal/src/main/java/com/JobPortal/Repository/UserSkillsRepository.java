package com.JobPortal.Repository;

import com.JobPortal.Model.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSkillsRepository extends JpaRepository<UserSkill, Long> {
    @Query("SELECT us FROM UserSkill us WHERE us.userProfile.id = :profileId")
    List<UserSkill> findUserSkillsByProfileId(Long profileId);

}
