package com.lister.employeeonboarding.controller;


import com.lister.employeeonboarding.DaoResponse.ResponseObject;
import com.lister.employeeonboarding.service.EmployeeDemographicsService;
import com.lister.employeeonboarding.voobject.EmployeeRequestVo;
import com.lister.employeeonboarding.voobject.StatusInfo;
import com.lister.employeeonboarding.service.HrService;

import com.lister.employeeonboarding.service.MailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;
/** Responsible for the Hr side of the application.
 * @author Indraneel
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@RestController
public class HrController {


    @Autowired
    private HrService hrService;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private EmployeeDemographicsService employeeDemographicsService;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * @param employeeRequestVo- to invite a hire
     * @return response entity with a success message
     */
    @CrossOrigin
    @PostMapping("/user")
    public ResponseEntity<Object> createUser(@RequestBody EmployeeRequestVo employeeRequestVo) {
        UUID uuid= UUID.randomUUID();
        hrService.createUser(employeeRequestVo,uuid);
        ResponseObject responseObject= new ResponseObject();
        responseObject.setMessage("User Invited");
        responseObject.setSuccess(true);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);


    }

    /**
     * @return response entity which has all the details as JSON of all the employees
     */
    @CrossOrigin
    @GetMapping("/users")
    public ResponseEntity<Object> getAllEmployeeDetails() {

        UUID uuid= UUID.randomUUID();
        return new ResponseEntity<>(hrService.allEmployeeInfo(uuid), HttpStatus.OK);

    }

    /**
     * @return the role data
     */
    @CrossOrigin
    @GetMapping("/roles")
    public ResponseEntity<Object> getAllRoles() {


        return new ResponseEntity<>(hrService.getRoles(UUID.randomUUID()), HttpStatus.OK);

    }

    /**
     * @param status for status update
     * @return response object with a success message
     */
    @CrossOrigin
    @PutMapping("/user/status")

    public ResponseEntity<Object> updateUserStatus( @RequestBody  StatusInfo status)
    {
        UUID uuid= UUID.randomUUID();
        ResponseObject responseObject= new ResponseObject();
        int employeeId= status.getId();
        boolean updateStatus=hrService.updateStatus(status, employeeId,uuid);
        if(updateStatus)
        {

        String message=restTemplate().postForObject("http://localhost:9292/user",hrService.fetchUserFinalData(employeeDemographicsService.fetchDetails(employeeId,uuid)),String.class);
        responseObject.setMessage(message);
        responseObject.setSuccess(true);
        return new ResponseEntity<>(responseObject,HttpStatus.OK);
}

        responseObject.setMessage("Status Updated SuccessFully");
        responseObject.setSuccess(true);
        return new ResponseEntity<>(responseObject, HttpStatus.OK);


    }

    /**
     * @param employeeId to send the notification for a particular employee
     * @return response object with success message
     */
    @CrossOrigin
    @PostMapping("/user/{employeeId}/notification")
    public ResponseEntity<Object> sendNotification(@PathVariable int employeeId) {


        return new ResponseEntity<>(hrService.notifyUser(employeeId,UUID.randomUUID()), HttpStatus.OK);


    }

}
