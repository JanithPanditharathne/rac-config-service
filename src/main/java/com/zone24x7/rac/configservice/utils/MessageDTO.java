package com.zone24x7.rac.configservice.utils;

/**
 * Class representing a message DTO.
 *
 */
public class MessageDTO {

    private String status;
    private String code;
    private String message;

    public MessageDTO(String status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public MessageDTO(String status, String message) {
        this.status = status;
        this.message = message;
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
