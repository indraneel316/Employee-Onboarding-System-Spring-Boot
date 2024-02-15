package com.lister.employeeonboarding.voobject;
/**
 * Vo for inviting a new hire
 */
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Data
@Setter
public class EmployeeRequestVo {
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private int role;
    @NotNull
    private String password;
}
