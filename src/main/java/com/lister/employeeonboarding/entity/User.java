package com.lister.employeeonboarding.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.lister.employeeonboarding.voobject.EmployeeDemographicsVo;
import lombok.*;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
/** Represents Employee authentication details
 * @author Indraneel
 * @version 1.0
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Users")
public class User implements Serializable {
    @Id
    @GeneratedValue
    private int sno;
    @Column
    @NotNull
    private String emailId;
    @Column
    @NotNull
    @Size(min = 8, message = "Password should have atleast 8 characters")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[$@!%*?&])[A-Za-z\\d$@!%*?&].{8,}", message = "Password Pattern Incorrect")
    private String password;
    @Column
    @NotNull
    private int roleId;
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private EmployeeDemographics userId;

}
