package com.lister.employeeonboarding.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


/** Represents the addresses of the employee.
 * @version 1.0
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Address")
public class Address implements Serializable {

    @Id
    @GeneratedValue
    private int addressId;
    @Column
    private String employeeCode;
    @Column
    @NotNull

    private String type;
    @Column
    @NotNull
    private String flatName;
    @Column
    @NotNull
    private String streetName;
    @Column
    @NotNull

    private String area;
    @Column
    @NotNull

    private String state;

    @Column
    @NotNull

    private String country;
    @Column
    @NotNull

    private String city;
    @Column
    @NotNull
    private String pinCode;
    @Column
    @NotNull
    private String mapCoordinates;
}