package com.trustbank.Model;

public class CrediCalModel {
    private String schemename;
    private String IsInsurance;
    private String IntBaseDays;
    private String InsuranceRate;

    public String getSchemename() {
        return schemename;
    }

    public void setSchemename(String schemename) {
        this.schemename = schemename;
    }

    public String getIsInsurance() {
        return IsInsurance;
    }

    public void setIsInsurance(String isInsurance) {
        IsInsurance = isInsurance;
    }

    public String getIntBaseDays() {
        return IntBaseDays;
    }

    public void setIntBaseDays(String intBaseDays) {
        IntBaseDays = intBaseDays;
    }

    public String getInsuranceRate() {
        return InsuranceRate;
    }

    public void setInsuranceRate(String insuranceRate) {
        InsuranceRate = insuranceRate;
    }
}
