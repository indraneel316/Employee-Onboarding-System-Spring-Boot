package com.lister.employeeonboarding.voobject;
/**
 * Vo for login
 */
import lombok.*;

@Data

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private String emailId;
    private String password;
}
