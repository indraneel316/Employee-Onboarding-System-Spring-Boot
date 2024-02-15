package com.lister.employeeonboarding.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
/**
 * Global exception handler for the system
 */
@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {
    @Autowired
    private EmployeeNotFoundException employeeNotFoundException;

    /**
     * @param ex custom exception
     * @return  response object with exception message
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationExceptions(
            ConstraintViolationException ex) {
        String exceptionResponse = String.format("Invalid input parameters: %s%n", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * @param e custom exception
     * @return  response object with exception message
     */
    @ExceptionHandler(InvalidException.class)
    public final ResponseEntity<Object> handleInvalidException(InvalidException e) {

        return new ResponseEntity<>(e.getErrorMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * @param e custom exception
     * @return  response object with exception message
     */
    @ExceptionHandler(EmployeeNotFoundException.class)
    public final ResponseEntity<Object> handleNotValidUserException(EmployeeNotFoundException e) {

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


}