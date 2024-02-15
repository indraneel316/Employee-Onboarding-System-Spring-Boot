package com.lister.employeeonboarding.repository;


import com.lister.employeeonboarding.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Responsible for role table
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String role);
}
