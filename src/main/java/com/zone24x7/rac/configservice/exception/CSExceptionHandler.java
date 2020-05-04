package com.zone24x7.rac.configservice.exception;

import com.zone24x7.rac.configservice.util.CSResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CSExceptionHandler {

    /**
     * Handle all the cs exceptions and validation errors.
     *
     * @param exception Validation exception.
     * @return CS response message with correct code and message.
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CSResponse handleValidationException(ValidationException exception) {

        // Split input message to get.
        // arr[1] = exception code.
        // arr[2] = exception message.
        String[] arr = exception.getMessage().split(":");

        // Return response.
        return new CSResponse("ERROR", arr[0], arr[1]);
    }



    /**
     * Handle all the validation exceptions.
     *
     * @param exception Validation exception.
     * @return CS response message with correct code and message.
     */
    @ExceptionHandler(ServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CSResponse handleServerException(ServerException exception) {

        // Split input message to get.
        // arr[1] = exception code.
        // arr[2] = exception message.
        String[] arr = exception.getMessage().split(":");

        // Return response.
        return new CSResponse("ERROR", arr[0], arr[1]);
    }



}
