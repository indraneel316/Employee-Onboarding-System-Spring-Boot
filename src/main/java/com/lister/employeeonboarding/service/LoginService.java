package com.lister.employeeonboarding.service;

import com.lister.employeeonboarding.entity.EmployeeDemographics;
import com.lister.employeeonboarding.exception.EmployeeNotFoundException;
import com.lister.employeeonboarding.entity.User;

import com.lister.employeeonboarding.repository.EmployeeDemographicsRepository;
import com.lister.employeeonboarding.repository.RoleRepository;
import com.lister.employeeonboarding.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/** Identifies the employee.
 * @author Indraneel
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
public class LoginService {

    @Autowired
    public UserRepository userRepository;
    @Autowired
    public EmployeeDemographicsRepository employeeDemographicsRepository;
    @Autowired
    public RoleRepository role;


    /**
     * @param emailId- emailId to verify login
     * @param password- password to verify login
     * @param uuid- unique number for logging
     * @return List of string type which contains the type of user, empId, status and role
     * @Exception Throws Custom exception if the email/password is invalid
     */
    public List<String> loginInfo(String emailId, String password, UUID uuid) {
        ArrayList<String> loginInfo = new ArrayList<>();


        User loginService = userRepository.findByEmailIdAndPassword(emailId, password);

        if (loginService != null) {

                if(loginService.getRoleId()!=role.findByRoleName("HR").getRoleId())
                {
            loginInfo.add(String.valueOf(loginService.getUserId().getEmployeeId()));
            loginInfo.add(String.valueOf(loginService.getRoleId()));
            loginInfo.add(loginService.getUserId().getCurrentStatus());
            if (loginService.getUserId().getAadharNumber() != null) {
                loginInfo.add("updated user");
            } else {
                loginInfo.add("new user");
            }}
                else
                {
                    loginInfo.add(String.valueOf(loginService.getRoleId()));
                }


        } else {
            log.error("Employee With email id " + emailId + " cant be accessed {}",uuid);
            throw new EmployeeNotFoundException("Employee With email id " + emailId + " is not found. Please Provide the correct details");
        }
        log.info("login details returned" + loginInfo+"{}",uuid);
        return loginInfo;
    }

//    public boolean checkUserType(String email) {
//
//        EmployeeDemographics employeeDemographics = employeeDemographicsRepository.findByEmailId(email);
//
//        if (employeeDemographics.getAadharNumber() != null) {
//            return true;
//        } else {
//            return false;
//        }
//    }
}
