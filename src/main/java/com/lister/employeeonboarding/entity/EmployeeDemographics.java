package com.lister.employeeonboarding.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
/** Represents an employee.
 * @author Indraneel
 * @version 1.0
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "basic_details_data")
public class EmployeeDemographics implements Serializable {

    @Id
    @GeneratedValue
    private int employeeId;
    @Column(unique = true)
    private String employeeCode;
    @NotNull
    @Pattern(regexp = "[a-zA-Z]*")
    private String firstName;
    @NotNull
    @Pattern(regexp = "[a-zA-Z]*")
    private String lastName;
    @Column
    private String dob;
    @Column
    private String bloodGroup;
    @Column(unique = true)

    @Pattern(regexp = "[0-9]*", message = "Invalid Aadhar Number")
    private String aadharNumber;
    @Column
    @Pattern(regexp = "[a-zA-Z]*")
    private String fatherName;
    @Column
    @Pattern(regexp = "[a-zA-Z]*")
    private String motherName;
    @Column(unique = true)

    private String emailId;
    @Column(unique = true)
    @Pattern(regexp = "^((\\+91-?)|0)?[0-9]{10}$")

    private String phoneNumber;
    @Column

    private Double sslc;

    @Column

    private Double hsc;

    @Column
    private Double ug;
    @Column
    @NotNull
    private int role;


    @Column
    private String gender;


    @Column
    @Pattern(regexp = "[a-zA-Z]*")

    private String emergencyContactName;
    @Column

    private String emergencyContactRelation;
    @Column(unique = true)

    @Pattern(regexp = "^((\\+91-?)|0)?[0-9]{10}$")
    private String emergencyContactPhoneNumber;
    @Column
    private String password;
    @Column
    @CreationTimestamp
    private Timestamp createdAt;
    @Column
    private String currentStatus;
    @Column
    private String rejectReason;
    @JsonIgnoreProperties
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_value")
    private List<Address> addressList;


}

