package com.zone24x7.rac.configservice.utils;

/**
 * Class representing a response DTO.
 *
 */
public class Response {

    private String status;
    private String code;
    private String message;

    public Response(String status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public Response(String status, String responseCode) {
        String[] responseCodeArray = responseCode.split(":");

        this.status = status;
        this.code = responseCodeArray[0];
        this.message = responseCodeArray[1].trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
