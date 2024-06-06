package com.JobPortal.Repository;

import com.JobPortal.Model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
public interface UserProfileRepository extends JpaRepository<UserProfile ,Long> {
}
