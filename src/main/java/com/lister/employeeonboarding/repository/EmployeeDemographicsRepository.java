package com.lister.employeeonboarding.repository;

/**
 * Responsible for basic employee details
 */
import com.lister.employeeonboarding.entity.EmployeeDemographics;
import com.lister.employeeonboarding.voobject.EmployeeDemographicsVo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDemographicsRepository extends JpaRepository<EmployeeDemographics, Integer> {

    EmployeeDemographics findByEmailId(String email);

    EmployeeDemographics findByEmployeeId(int empId);

    @Query(value = "SELECT MAX(employee_id) FROM basic_details_data b",nativeQuery = true)
    Integer findMaxCode();


}

