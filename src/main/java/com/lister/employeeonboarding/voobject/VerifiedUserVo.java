package com.lister.employeeonboarding.voobject;
/**
 * Vo for original database for the approved employees
 */
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class VerifiedUserVo implements Serializable {
    @NotNull
    private int employeeId;
    @NotNull
    private String employeeCode;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String dob;
    private String bloodGroup;
    private String aadharNumber;
    private String fatherName;
    private String motherName;
    private String emailId;
    private String phoneNumber;
    private Double sslc;
    private Double hsc;
    private Double ug;
    private String gender;
    private String emergencyContactName;
    private String emergencyContactRelation;
    private String emergencyContactPhoneNumber;
    private List<Address> addressList;


}
