package com.lister.employeeonboarding.controller;


import com.lister.employeeonboarding.service.LoginService;
import com.lister.employeeonboarding.voobject.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/** Responsible for login functionality.
 * @author Indraneel
 * @version 1.0
 */
import java.util.List;
import java.util.UUID;

@RestController

public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * @param user- user vo to verify login
     * @return responseEntity with login status of the employee, type of employee, role, empId
     */
    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<Object> userLogin(@RequestBody UserVo user) {


        List<String> userIdentityDetails;

        userIdentityDetails = loginService.loginInfo(user.getEmailId(), user.getPassword(), UUID.randomUUID());
        if (userIdentityDetails != null) {


            return new ResponseEntity<>(userIdentityDetails, HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid Credentials", HttpStatus.BAD_REQUEST);

    }
}
