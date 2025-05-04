package com.trustbank.Model;

import java.io.Serializable;

public class AmortizationChartDetailsListInfo implements Serializable {

    String installmentNo;
    String expectedDate;
    String interestAmount;
    String principleAmount;
    String installmentAmount;
    String balance;

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getInstallmentNo() {
        return installmentNo;
    }

    public void setInstallmentNo(String installmentNo) {
        this.installmentNo = installmentNo;
    }

    public String getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(String interestAmount) {
        this.interestAmount = interestAmount;
    }

    public String getPrincipleAmount() {
        return principleAmount;
    }

    public void setPrincipleAmount(String principleAmount) {
        this.principleAmount = principleAmount;
    }

    public String getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(String installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
