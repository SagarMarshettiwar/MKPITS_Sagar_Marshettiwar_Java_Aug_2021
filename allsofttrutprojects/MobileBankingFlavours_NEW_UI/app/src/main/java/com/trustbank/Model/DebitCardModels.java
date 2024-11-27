package com.trustbank.Model;

import androidx.annotation.NonNull;

public class DebitCardModels {

    private String debitCardNo;
    private String debitCardNoForDisplay;
    private String status;
    private String ATMLimit;
    private String ECOMLimit;
    private String POSLimit;
    private String ContactlessLimit;
    private String ATMStatus;
    private String POSStatus;
    private String ECOMStatus;
    private String ContactlessStatus;
    private String maskedCardNo;

    public String getMaskedCardNo() {
        return maskedCardNo;
    }

    public void setMaskedCardNo(String maskedCardNo) {
        this.maskedCardNo = maskedCardNo;
    }

    public String getATMStatus() {
        return ATMStatus;
    }

    public void setATMStatus(String ATMStatus) {
        this.ATMStatus = ATMStatus;
    }

    public String getPOSStatus() {
        return POSStatus;
    }

    public void setPOSStatus(String POSStatus) {
        this.POSStatus = POSStatus;
    }

    public String getECOMStatus() {
        return ECOMStatus;
    }

    public void setECOMStatus(String ECOMStatus) {
        this.ECOMStatus = ECOMStatus;
    }

    public String getATMLimit() {
        return ATMLimit;
    }

    public void setATMLimit(String ATMLimit) {
        this.ATMLimit = ATMLimit;
    }

    public String getECOMLimit() {
        return ECOMLimit;
    }

    public void setECOMLimit(String ECOMLimit) {
        this.ECOMLimit = ECOMLimit;
    }

    public String getPOSLimit() {
        return POSLimit;
    }

    public void setPOSLimit(String POSLimit) {
        this.POSLimit = POSLimit;
    }

    public String getDebitCardNo() {
        return debitCardNo;
    }

    public void setDebitCardNo(String debitCardNo) {
        this.debitCardNo = debitCardNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDebitCardNoForDisplay() {
        return debitCardNoForDisplay;
    }

    public void setDebitCardNoForDisplay(String debitCardNoForDisplay) {
        this.debitCardNoForDisplay = debitCardNoForDisplay;
    }

    @NonNull
    @Override
    public String toString() {
        return this.debitCardNoForDisplay; // What to display in the Spinner list.
    }

    public String getContactlessLimit() {
        return ContactlessLimit;
    }

    public void setContactlessLimit(String contactlessLimit) {
        ContactlessLimit = contactlessLimit;
    }

    public String getContactlessStatus() {
        return ContactlessStatus;
    }

    public void setContactlessStatus(String contactlessStatus) {
        ContactlessStatus = contactlessStatus;
    }
}
