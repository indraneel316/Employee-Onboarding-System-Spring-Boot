package com.lister.employeeonboarding.voobject;
/**
 * Vo for updating an employee's status
 */
import lombok.*;

@Data

@Getter
@Setter
public class StatusInfo {
    private int id;
    private String statusType;
    private String rejectReason;


}
