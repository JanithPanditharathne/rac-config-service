package com.zone24x7.rac.configservice.exception;

import com.zone24x7.rac.configservice.util.CSResponse;
import com.zone24x7.rac.configservice.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CSExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Handle all the invalid request exceptions.
     *
     * @return CS response with "CS-911" code and "Invalid request" message.
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        // Log the exception.
        logger.info(exception.getMessage(), exception);

        // Return response.
        String message = "Invalid request (" +  exception.getMessage() + ")";
        return new ResponseEntity<>(new CSResponse(Strings.ERROR, "CS-911", message), status);
    }


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

        // Log the exception.
        logger.info(exception.getMessage(), exception);

        // Split input message to get.
        // arr[1] = exception code.
        // arr[2] = exception message.
        String[] arr = exception.getMessage().split(":");

        // Return response.
        return new CSResponse(Strings.ERROR, arr[0], arr[1].trim());
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

        // Log the exception.
        logger.info(exception.getMessage(), exception);

        // Split input message to get.
        // arr[1] = exception code.
        // arr[2] = exception message.
        String[] arr = exception.getMessage().split(":");

        // Return response.
        return new CSResponse(Strings.FAIL, arr[0], arr[1].trim());
    }






}
