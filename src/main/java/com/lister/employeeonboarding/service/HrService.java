package com.lister.employeeonboarding.service;


import com.lister.employeeonboarding.DaoResponse.ResponseObject;
import com.lister.employeeonboarding.exception.EmployeeNotFoundException;
import com.lister.employeeonboarding.exception.InvalidException;
import com.lister.employeeonboarding.entity.*;
import com.lister.employeeonboarding.statusenum.Status;
import com.lister.employeeonboarding.repository.EmployeeDemographicsRepository;
import com.lister.employeeonboarding.repository.RoleRepository;
import com.lister.employeeonboarding.repository.UserRepository;
import com.lister.employeeonboarding.voobject.EmployeeRequestVo;
import com.lister.employeeonboarding.voobject.StatusInfo;
import com.lister.employeeonboarding.voobject.VerifiedUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/** Helps the Hr to invite a hire, update the status of the hire, to view all the details of each hire and all the hires as well.
 * @author Indraneel
 * @version 1.0
 * @since 1.0
 *
 */
@Slf4j
@Service
public class HrService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployeeDemographicsRepository employeeDemographicsRepository;

    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private RoleRepository roleRepository;

    /**
     * @param employeeRequestVo- Vo for creating invite
     * @param uuid- unique id to track the logging
     */
    @Validated
    public void createUser(@Valid EmployeeRequestVo employeeRequestVo,UUID uuid) {//createUser

        EmployeeDemographics employeeDemographics = new EmployeeDemographics();
        User user = new User();


        employeeDemographics.setFirstName(employeeRequestVo.getName());
        employeeDemographics.setLastName(employeeRequestVo.getName());
        employeeDemographics.setEmailId(employeeRequestVo.getEmail());
        employeeDemographics.setRole(employeeRequestVo.getRole());

        employeeDemographics.setCurrentStatus(Status.INCOMPLETE.toString());

        Integer latestId=employeeDemographicsRepository.findMaxCode();
        if(latestId==null||latestId<=0)
        {
            latestId=1;
        }


        String code = "LIS" + latestId;
        employeeDemographics.setEmployeeCode(code);

        user.setEmailId(employeeRequestVo.getEmail());
        user.setPassword(employeeRequestVo.getPassword());



        user.setRoleId(employeeRequestVo.getRole());


        employeeDemographicsRepository.save(employeeDemographics);
        log.info("Employee Demographics {} Saved in Repository {}",employeeDemographics,uuid);

        EmployeeDemographics employeeDemographicsRepositoryByEmailId = employeeDemographicsRepository.findByEmailId(employeeRequestVo.getEmail());
        if (employeeDemographicsRepositoryByEmailId != null) {

            user.setUserId(employeeDemographicsRepositoryByEmailId);
            userRepository.save(user);
            log.info("User Saved {}" + user,uuid);

        } else {
            log.error("Employee With this Email Id is not found {}",uuid);
            throw new EmployeeNotFoundException("Employee With this Email Id is not found");
        }


    }

    /**
     * @param role- roleName to fetch roleId
     * @return roleId
     */
    public int roleIdGenerator(String role) {
        if (role != null) {
            Role roleObject = roleRepository.findByRoleName(role);
            log.info("Role ID Retrieved" + roleObject.getRoleId());
            return roleObject.getRoleId();

        } else {
            log.error("Role is Null");
            throw new InvalidException("Role is Null");
        }


    }

    /**
     * @param uuid - unique id to track the logging
     * @return List of all employees with details
     */
    public List<EmployeeDemographics> allEmployeeInfo(UUID uuid) {
        log.info("Employees Data Fetched {}",uuid);
        return employeeDemographicsRepository.findAll();//return this alone


    }

    /**
     * @param uuid- Unique id to track logging
     * @return list of available roles in the system
     */
    public List<Role> getRoles(UUID uuid) {
        log.info("Getting all roles from database {}",uuid);
        return roleRepository.findAll();
    }

    /**
     * @param status -statusVo for updating the status of the new hire
     * @param employeeId- to update the corresponding user
     * @param uuid- unique id to track logging
     * @return boolean value if the action is approve then it is returned true else false
     * @Exception Throws custom exception if the employee id is not found
     */
    public boolean updateStatus(StatusInfo status, int employeeId,UUID uuid) {

       boolean isStatusApproved = false;
        EmployeeDemographics statusOfEmployee = employeeDemographicsRepository.findByEmployeeId(employeeId);
        if (statusOfEmployee != null) {

            if (status.getStatusType().equalsIgnoreCase("Approve")) {
                status.setStatusType(Status.COMPLETED.toString());
                isStatusApproved = true;

            }
            if (status.getStatusType().equalsIgnoreCase("Reject")) {
                status.setStatusType(Status.REJECTED.toString());

            }
            statusOfEmployee.setCurrentStatus(status.getStatusType());
            statusOfEmployee.setRejectReason(status.getRejectReason());
            String toEmail = statusOfEmployee.getEmailId();
            employeeDemographicsRepository.save(statusOfEmployee);

            mailSenderService.sendSimpleEmail(toEmail, status.getStatusType(), status.getRejectReason(),uuid);
            log.info("Status Updated On Object {}",statusOfEmployee);
        } else {
            log.error("Employee ID" + employeeId + "is not valid for status update {}",uuid);
            throw new EmployeeNotFoundException("Employee Id Is not Valid");
        }

        return isStatusApproved;

    }

    /**
     * @param employeeId to get the employee email by id
     * @param uuid unique number to track logging
     * @return provides email
     * @Exception throws custom exception if the email is unavailable
     */
    public String getEmployeeEmailById(int employeeId,UUID uuid) {

        EmployeeDemographics employeeEmail = employeeDemographicsRepository.findByEmployeeId(employeeId);
        if (employeeEmail == null || employeeEmail.getEmailId() == null) {
            log.error("email id null while checking employee {}",uuid);
            throw new EmployeeNotFoundException("Employee With this Email is not Found");

        }


        log.info("employee email retrieved of object {} {}",employeeEmail,uuid);
        return employeeEmail.getEmailId();

    }

    /**
     * @param emp- all the details of the employee
     * @return required details of the employee to be pushed in the original database by calling second microservice
     */
    public VerifiedUserVo fetchUserFinalData(EmployeeDemographics emp)
    { VerifiedUserVo verifiedUserVo= new VerifiedUserVo();
        verifiedUserVo.setFirstName(emp.getFirstName());
        verifiedUserVo.setLastName(emp.getLastName());
        verifiedUserVo.setDob(emp.getDob());
        verifiedUserVo.setAadharNumber(emp.getAadharNumber());
        verifiedUserVo.setEmailId(emp.getEmailId());
        verifiedUserVo.setEmployeeId(emp.getEmployeeId());
        verifiedUserVo.setEmployeeCode(emp.getEmployeeCode());
        verifiedUserVo.setBloodGroup(emp.getBloodGroup());
        verifiedUserVo.setGender(emp.getGender());
        verifiedUserVo.setFatherName(emp.getFatherName());
        verifiedUserVo.setMotherName(emp.getMotherName());
        verifiedUserVo.setAddressList(emp.getAddressList());
        verifiedUserVo.setHsc(emp.getHsc());
        verifiedUserVo.setSslc(emp.getSslc());
        verifiedUserVo.setUg(emp.getUg());
        verifiedUserVo.setPhoneNumber(emp.getPhoneNumber());
        verifiedUserVo.setEmergencyContactName(emp.getEmergencyContactName());
        verifiedUserVo.setEmergencyContactRelation(emp.getEmergencyContactRelation());
        verifiedUserVo.setEmergencyContactPhoneNumber(emp.getEmergencyContactPhoneNumber());
        return verifiedUserVo;
    }

    /**
     * @param employeeId- to fetch emailId of an employee for sending a mail
     * @param uuid- unique number for tracking purposes
     * @return Response object
     */
    public ResponseObject notifyUser(int employeeId,UUID uuid)
    {

        mailSenderService.sendSimpleEmail( getEmployeeEmailById(employeeId,UUID.randomUUID()), "Notify", "",UUID.randomUUID());
        log.info("User Notified {}",uuid);
        ResponseObject responseObject= new ResponseObject();
        responseObject.setMessage("User Notified");
        responseObject.setSuccess(true);
        return responseObject;
    }

}
