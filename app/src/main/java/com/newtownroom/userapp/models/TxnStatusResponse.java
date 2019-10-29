package com.newtownroom.userapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TxnStatusResponse {

    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("errorCode")
    private int errorCode;

    @SerializedName("responseCode")
    private int responseCode;

    @SerializedName("result")
    private ArrayList<PaymentResult> result;

    public TxnStatusResponse() {
    }

    public TxnStatusResponse(int status, String message, int errorCode, int responseCode, ArrayList<PaymentResult> result) {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
        this.responseCode = responseCode;
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public ArrayList<PaymentResult> getResult() {
        return result;
    }

    public void setResult(ArrayList<PaymentResult> result) {
        this.result = result;
    }

    public class PaymentResult {

        @SerializedName("merchantTransactionId")
        private String merchantTransactionId;

        @SerializedName("paymentId")
        private double paymentId;

        @SerializedName("status")
        private String status;

        @SerializedName("amount")
        private float amount;

        public PaymentResult() {
        }

        public PaymentResult(String merchantTransactionId, double paymentId, String status, float amount) {
            this.merchantTransactionId = merchantTransactionId;
            this.paymentId = paymentId;
            this.status = status;
            this.amount = amount;
        }

        public String getMerchantTransactionId() {
            return merchantTransactionId;
        }

        public void setMerchantTransactionId(String merchantTransactionId) {
            this.merchantTransactionId = merchantTransactionId;
        }

        public double getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(double paymentId) {
            this.paymentId = paymentId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }
    }
}
