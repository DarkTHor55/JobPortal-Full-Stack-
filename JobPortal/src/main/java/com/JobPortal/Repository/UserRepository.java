package com.JobPortal.Repository;

import com.JobPortal.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByEmailAndStatus(String email, String status);


    User findByRoleAndStatusIn(String role, List<String> status);

    List<User> findByRole(String role);

    User findByEmailAndRoleAndStatus(String emailId, String role, String status);

    List<User> findByRoleAndStatus(String role, String status);
}
