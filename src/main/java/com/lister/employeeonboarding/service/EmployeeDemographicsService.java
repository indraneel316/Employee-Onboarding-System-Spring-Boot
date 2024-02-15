package com.lister.employeeonboarding.service;


import com.lister.employeeonboarding.DaoResponse.ResponseObject;
import com.lister.employeeonboarding.entity.Address;
import com.lister.employeeonboarding.entity.EmployeeDemographics;
import com.lister.employeeonboarding.exception.EmployeeNotFoundException;
import com.lister.employeeonboarding.voobject.EmployeeDemographicsVo;
import com.lister.employeeonboarding.statusenum.Status;
import com.lister.employeeonboarding.repository.AddressRepository;
import com.lister.employeeonboarding.repository.EmployeeDemographicsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/** Represents an employee.
 * @author Indraneel
 * @version 1.0
 * @since 1.0
 */
@Service
@Slf4j
public class EmployeeDemographicsService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private EmployeeDemographicsRepository employeeDemographicsRepository;

    /**
     * @param formData- whole demographics data when user gives save/ submit action in the UI
     * @param empId- employee Id to update corresponding details
     * @param uuid- unique number for logging purposes
     * @return Response Object which indicates that use is saved successfully
     * @exception throws employee not found exception when the incorrect credentials are given
     */
    public ResponseObject updateDetails(EmployeeDemographicsVo formData, int empId, UUID uuid) {

        EmployeeDemographics existingBasicDetails = employeeDemographicsRepository.findByEmployeeId(empId);
        if (existingBasicDetails != null) {
            existingBasicDetails.setFirstName(formData.getFirstName());
            existingBasicDetails.setLastName(formData.getLastName());
            existingBasicDetails.setEmailId(formData.getEmailId());
            existingBasicDetails.setAadharNumber(formData.getAadharNumber());
            existingBasicDetails.setPhoneNumber(formData.getPhoneNumber());
            existingBasicDetails.setBloodGroup(formData.getBloodGroup());
            existingBasicDetails.setDob(formData.getDob());
            if (formData.getAction().equalsIgnoreCase("Submit")) {
                existingBasicDetails.setCurrentStatus(Status.PENDING.toString());
            } else {
                existingBasicDetails.setCurrentStatus(Status.INCOMPLETE.toString());
            }
            existingBasicDetails.setFatherName(formData.getFatherName());
            existingBasicDetails.setMotherName(formData.getMotherName());
            existingBasicDetails.setGender(formData.getGender());
            existingBasicDetails.setEmergencyContactName(formData.getEmergencyContactName());
            existingBasicDetails.setEmergencyContactPhoneNumber(formData.getEmergencyContactPhoneNumber());
            existingBasicDetails.setEmergencyContactRelation(formData.getEmergencyContactRelation());
            existingBasicDetails.setSslc(formData.getSslc());
            existingBasicDetails.setHsc(formData.getHsc());
            existingBasicDetails.setUg(formData.getUg());
            existingBasicDetails.setRejectReason("Not Rejected Yet");


            formData.getAddressList().get(0).setType("Present");
            formData.getAddressList().get(1).setType("Permanent");
            formData.getAddressList().get(0).setEmployeeCode(existingBasicDetails.getEmployeeCode());
            formData.getAddressList().get(1).setEmployeeCode(existingBasicDetails.getEmployeeCode());
            if (existingBasicDetails.getAddressList().isEmpty()) {
                existingBasicDetails.setAddressList(formData.getAddressList());
            } else{
                for(int i=0;i< existingBasicDetails.getAddressList().size();i++)
                {   existingBasicDetails.getAddressList().get(i).setEmployeeCode(formData.getAddressList().get(i).getEmployeeCode());
                    existingBasicDetails.getAddressList().get(i).setType(formData.getAddressList().get(i).getType());
                existingBasicDetails.getAddressList().get(i).setArea(formData.getAddressList().get(i).getArea());
                existingBasicDetails.getAddressList().get(i).setCountry(formData.getAddressList().get(i).getCountry());
                existingBasicDetails.getAddressList().get(i).setMapCoordinates(formData.getAddressList().get(i).getMapCoordinates());
                existingBasicDetails.getAddressList().get(i).setPinCode(formData.getAddressList().get(i).getPinCode());
                existingBasicDetails.getAddressList().get(i).setFlatName(formData.getAddressList().get(i).getFlatName());
                existingBasicDetails.getAddressList().get(i).setState(formData.getAddressList().get(i).getState());
                existingBasicDetails.getAddressList().get(i).setStreetName(formData.getAddressList().get(i).getStreetName());
                existingBasicDetails.getAddressList().get(i).setCity(formData.getAddressList().get(i).getCity());



            }}


            employeeDemographicsRepository.save(existingBasicDetails);
            log.info("Employee saved {} " + existingBasicDetails,uuid);

        } else {
            log.error("Error : Employee with ID  " + empId + " is invalid  {}",uuid);
            throw new EmployeeNotFoundException("Employee With ID" + empId + " is invalid");
        }

        ResponseObject responseObject= new ResponseObject();
        responseObject.setMessage("Details Updated Successfully");
        responseObject.setSuccess(true);
        return responseObject;


    }


    /**
     * @param employeeId- to fetch the details of corresponding employee
     * @param uuid- unique number to track the logging
     * @return EmployeeDemographics which contains the basic details and address details of the employee
     */
    public EmployeeDemographics fetchDetails(int employeeId,UUID uuid) {

        EmployeeDemographics employee= employeeDemographicsRepository.findByEmployeeId(employeeId);
        if (employee != null) {

            log.info("Employee Object returned {} {}" ,employee,uuid);
            return employee;
        } else {
            log.error("Employee With ID " + employeeId + "Cannot be accessed {}",uuid);
            throw new EmployeeNotFoundException("Employee With ID  " + employeeId + " Not Found ");
        }


    }
}
