
package com.newtownroom.userapp.models;

import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class PayUResponse {

    @Expose
    private Object errorCode;
    @Expose
    private String message;
    @Expose
    private Object responseCode;
    @Expose
    private Result result;
    @Expose
    private Long status;

    public Object getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Object responseCode) {
        this.responseCode = responseCode;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

}
