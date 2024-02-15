package com.lister.employeeonboarding.controller;



import com.lister.employeeonboarding.repository.EmployeeDemographicsRepository;
import com.lister.employeeonboarding.voobject.EmployeeDemographicsVo;
import com.lister.employeeonboarding.service.EmployeeDemographicsService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.UUID;

/** Responsible for employee side of the application.
 * @author Indraneel
 * @version 1.0
 * @since 1.0
 */
@RestController

public class EmployeeController {

    Logger log = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeDemographicsService employeeDemographicsService;

    @Autowired
    private EmployeeDemographicsRepository employeeDemographicsRepository;

    /**
     * @param employeeId- to submit the data of the corresponding user
     * @param formData- Vo which contains all the details of the user
     * @return Response Object with a success message
     */
    @CrossOrigin
    @PutMapping("/details/{employeeId}")
    public ResponseEntity<Object> submitData(@PathVariable int employeeId, @RequestBody @Valid EmployeeDemographicsVo formData) {
        UUID uuid= UUID.randomUUID();
        log.info("Request ID {} {}", employeeId,uuid);
        log.info("Request Body {} {}", formData,uuid);




        return new ResponseEntity<>(employeeDemographicsService.updateDetails(formData, employeeId,uuid), HttpStatus.OK);


    }

    /**
     * @param employeeId-to fetch a particular employee
     * @return Response Entity with success message and the details of employee.
     */
    @CrossOrigin
    @GetMapping("/details/{employeeId}")
    public ResponseEntity<Object> getEmployeeDataById(@PathVariable int employeeId) {
        UUID uuid= UUID.randomUUID();
       log.info("fetching details for id {} {} ",employeeId,uuid);

        return new ResponseEntity<>(employeeDemographicsService.fetchDetails(employeeId,uuid), HttpStatus.OK);

    }

}
