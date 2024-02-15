package com.lister.employeeonboarding.repository;


import com.lister.employeeonboarding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * responsible for user table
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {


    User findByEmailIdAndPassword(String emailId, String password);
}
