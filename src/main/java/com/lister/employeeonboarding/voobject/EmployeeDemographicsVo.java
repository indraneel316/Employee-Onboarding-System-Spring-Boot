package com.lister.employeeonboarding.voobject;

import com.fasterxml.jackson.annotation.*;
import com.lister.employeeonboarding.entity.Address;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Vo for employee Demographics
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class EmployeeDemographicsVo implements Serializable {

    private int employeeId;

    private String employeeCode;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String dob;
    private String bloodGroup;
    @Pattern(regexp = "[0-9]*", message = "Invalid Aadhar Number")
    private String aadharNumber;

    @Pattern(regexp = "[a-zA-Z]*")
    private String fatherName;
    @Pattern(regexp = "[a-zA-Z]*")
    private String motherName;
    @NotNull
    private String emailId;
    @Pattern(regexp = "^((\\+91-?)|0)?[0-9]{10}$")
    private String phoneNumber;
    @NotNull

    private Double sslc;
    @NotNull
    private Double hsc;
    @NotNull

    private Double ug;


    @NotNull
    private String gender;


    @Column
    @Pattern(regexp = "[a-zA-Z]*")
    private String emergencyContactName;
    //    @Column
    private String emergencyContactRelation;
    @Column(unique = true)

    @Pattern(regexp = "^((\\+91-?)|0)?[0-9]{10}$")
    @NotNull
    private String emergencyContactPhoneNumber;
    private String password;


    private String currentStatus;

    private String rejectReason;
    private List<Address> addressList;
    @NotNull
    private String action;


}
