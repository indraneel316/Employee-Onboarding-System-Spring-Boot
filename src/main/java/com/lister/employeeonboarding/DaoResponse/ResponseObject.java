package com.lister.employeeonboarding.DaoResponse;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
/** Represents the response in the UI.
 */
public class ResponseObject {
    Boolean success;
    String message;
    String userType;
}

