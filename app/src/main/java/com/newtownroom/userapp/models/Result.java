
package com.newtownroom.userapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Result {

    @Expose
    private String addedon;
    @Expose
    private String additionalCharges;
    @SerializedName("additional_param")
    private String additionalParam;
    @Expose
    private String address1;
    @Expose
    private String address2;
    @Expose
    private String amount;
    @SerializedName("amount_split")
    private String amountSplit;
    @SerializedName("bank_ref_num")
    private String bankRefNum;
    @Expose
    private String bankcode;
    @Expose
    private Object baseUrl;
    @Expose
    private Boolean calledStatus;
    @SerializedName("card_merchant_param")
    private Object cardMerchantParam;
    @Expose
    private String cardToken;
    @SerializedName("card_type")
    private String cardType;
    @Expose
    private String cardhash;
    @Expose
    private String cardnum;
    @Expose
    private String city;
    @Expose
    private String country;
    @Expose
    private Long createdOn;
    @Expose
    private String discount;
    @Expose
    private String email;
    @Expose
    private Object encryptedPaymentId;
    @Expose
    private String error;
    @SerializedName("error_Message")
    private String errorMessage;
    @Expose
    private Object fetchAPI;
    @Expose
    private String field1;
    @Expose
    private String field2;
    @Expose
    private String field3;
    @Expose
    private String field4;
    @Expose
    private String field5;
    @Expose
    private String field6;
    @Expose
    private String field7;
    @Expose
    private String field8;
    @Expose
    private String field9;
    @Expose
    private String firstname;
    @Expose
    private Object furl;
    @Expose
    private String hash;
    @Expose
    private Object id;
    @Expose
    private Long isConsentPayment;
    @Expose
    private String key;
    @Expose
    private String lastname;
    @Expose
    private String meCode;
    @Expose
    private Object merchantid;
    @Expose
    private String mihpayid;
    @Expose
    private String mode;
    @SerializedName("name_on_card")
    private String nameOnCard;
    @SerializedName("net_amount_debit")
    private String netAmountDebit;
    @SerializedName("offer_availed")
    private String offerAvailed;
    @SerializedName("offer_failure_reason")
    private String offerFailureReason;
    @SerializedName("offer_key")
    private String offerKey;
    @SerializedName("offer_type")
    private String offerType;
    @SerializedName("paisa_mecode")
    private String paisaMecode;
    @Expose
    private Long paymentId;
    @SerializedName("payment_source")
    private Object paymentSource;
    @Expose
    private String payuMoneyId;
    @SerializedName("pg_ref_no")
    private String pgRefNo;
    @SerializedName("pg_TYPE")
    private String pgTYPE;
    @Expose
    private String phone;
    @Expose
    private Long postBackParamId;
    @Expose
    private String postUrl;
    @Expose
    private String productinfo;
    @Expose
    private Long retryCount;
    @Expose
    private Boolean s2SPbpFlag;
    @Expose
    private String state;
    @Expose
    private String status;
    @Expose
    private Object surl;
    @Expose
    private String txnid;
    @Expose
    private String udf1;
    @Expose
    private String udf10;
    @Expose
    private String udf2;
    @Expose
    private String udf3;
    @Expose
    private String udf4;
    @Expose
    private String udf5;
    @Expose
    private String udf6;
    @Expose
    private String udf7;
    @Expose
    private String udf8;
    @Expose
    private String udf9;
    @Expose
    private String unmappedstatus;
    @Expose
    private String version;
    @Expose
    private String zipcode;

    public String getAddedon() {
        return addedon;
    }

    public void setAddedon(String addedon) {
        this.addedon = addedon;
    }

    public String getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(String additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    public String getAdditionalParam() {
        return additionalParam;
    }

    public void setAdditionalParam(String additionalParam) {
        this.additionalParam = additionalParam;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmountSplit() {
        return amountSplit;
    }

    public void setAmountSplit(String amountSplit) {
        this.amountSplit = amountSplit;
    }

    public String getBankRefNum() {
        return bankRefNum;
    }

    public void setBankRefNum(String bankRefNum) {
        this.bankRefNum = bankRefNum;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public Object getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(Object baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Boolean getCalledStatus() {
        return calledStatus;
    }

    public void setCalledStatus(Boolean calledStatus) {
        this.calledStatus = calledStatus;
    }

    public Object getCardMerchantParam() {
        return cardMerchantParam;
    }

    public void setCardMerchantParam(Object cardMerchantParam) {
        this.cardMerchantParam = cardMerchantParam;
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardhash() {
        return cardhash;
    }

    public void setCardhash(String cardhash) {
        this.cardhash = cardhash;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getEncryptedPaymentId() {
        return encryptedPaymentId;
    }

    public void setEncryptedPaymentId(Object encryptedPaymentId) {
        this.encryptedPaymentId = encryptedPaymentId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getFetchAPI() {
        return fetchAPI;
    }

    public void setFetchAPI(Object fetchAPI) {
        this.fetchAPI = fetchAPI;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    public String getField5() {
        return field5;
    }

    public void setField5(String field5) {
        this.field5 = field5;
    }

    public String getField6() {
        return field6;
    }

    public void setField6(String field6) {
        this.field6 = field6;
    }

    public String getField7() {
        return field7;
    }

    public void setField7(String field7) {
        this.field7 = field7;
    }

    public String getField8() {
        return field8;
    }

    public void setField8(String field8) {
        this.field8 = field8;
    }

    public String getField9() {
        return field9;
    }

    public void setField9(String field9) {
        this.field9 = field9;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Object getFurl() {
        return furl;
    }

    public void setFurl(Object furl) {
        this.furl = furl;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Long getIsConsentPayment() {
        return isConsentPayment;
    }

    public void setIsConsentPayment(Long isConsentPayment) {
        this.isConsentPayment = isConsentPayment;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMeCode() {
        return meCode;
    }

    public void setMeCode(String meCode) {
        this.meCode = meCode;
    }

    public Object getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(Object merchantid) {
        this.merchantid = merchantid;
    }

    public String getMihpayid() {
        return mihpayid;
    }

    public void setMihpayid(String mihpayid) {
        this.mihpayid = mihpayid;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getNetAmountDebit() {
        return netAmountDebit;
    }

    public void setNetAmountDebit(String netAmountDebit) {
        this.netAmountDebit = netAmountDebit;
    }

    public String getOfferAvailed() {
        return offerAvailed;
    }

    public void setOfferAvailed(String offerAvailed) {
        this.offerAvailed = offerAvailed;
    }

    public String getOfferFailureReason() {
        return offerFailureReason;
    }

    public void setOfferFailureReason(String offerFailureReason) {
        this.offerFailureReason = offerFailureReason;
    }

    public String getOfferKey() {
        return offerKey;
    }

    public void setOfferKey(String offerKey) {
        this.offerKey = offerKey;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getPaisaMecode() {
        return paisaMecode;
    }

    public void setPaisaMecode(String paisaMecode) {
        this.paisaMecode = paisaMecode;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Object getPaymentSource() {
        return paymentSource;
    }

    public void setPaymentSource(Object paymentSource) {
        this.paymentSource = paymentSource;
    }

    public String getPayuMoneyId() {
        return payuMoneyId;
    }

    public void setPayuMoneyId(String payuMoneyId) {
        this.payuMoneyId = payuMoneyId;
    }

    public String getPgRefNo() {
        return pgRefNo;
    }

    public void setPgRefNo(String pgRefNo) {
        this.pgRefNo = pgRefNo;
    }

    public String getPgTYPE() {
        return pgTYPE;
    }

    public void setPgTYPE(String pgTYPE) {
        this.pgTYPE = pgTYPE;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getPostBackParamId() {
        return postBackParamId;
    }

    public void setPostBackParamId(Long postBackParamId) {
        this.postBackParamId = postBackParamId;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getProductinfo() {
        return productinfo;
    }

    public void setProductinfo(String productinfo) {
        this.productinfo = productinfo;
    }

    public Long getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Long retryCount) {
        this.retryCount = retryCount;
    }

    public Boolean getS2SPbpFlag() {
        return s2SPbpFlag;
    }

    public void setS2SPbpFlag(Boolean s2SPbpFlag) {
        this.s2SPbpFlag = s2SPbpFlag;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getSurl() {
        return surl;
    }

    public void setSurl(Object surl) {
        this.surl = surl;
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getUdf1() {
        return udf1;
    }

    public void setUdf1(String udf1) {
        this.udf1 = udf1;
    }

    public String getUdf10() {
        return udf10;
    }

    public void setUdf10(String udf10) {
        this.udf10 = udf10;
    }

    public String getUdf2() {
        return udf2;
    }

    public void setUdf2(String udf2) {
        this.udf2 = udf2;
    }

    public String getUdf3() {
        return udf3;
    }

    public void setUdf3(String udf3) {
        this.udf3 = udf3;
    }

    public String getUdf4() {
        return udf4;
    }

    public void setUdf4(String udf4) {
        this.udf4 = udf4;
    }

    public String getUdf5() {
        return udf5;
    }

    public void setUdf5(String udf5) {
        this.udf5 = udf5;
    }

    public String getUdf6() {
        return udf6;
    }

    public void setUdf6(String udf6) {
        this.udf6 = udf6;
    }

    public String getUdf7() {
        return udf7;
    }

    public void setUdf7(String udf7) {
        this.udf7 = udf7;
    }

    public String getUdf8() {
        return udf8;
    }

    public void setUdf8(String udf8) {
        this.udf8 = udf8;
    }

    public String getUdf9() {
        return udf9;
    }

    public void setUdf9(String udf9) {
        this.udf9 = udf9;
    }

    public String getUnmappedstatus() {
        return unmappedstatus;
    }

    public void setUnmappedstatus(String unmappedstatus) {
        this.unmappedstatus = unmappedstatus;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

}
