package com.trustbank.Model;

public class InvestSlabModel {
    private String acno;
    private String schemeName;
    private String depositdate;
    private String maturitydate;
    private String depositamt;
    private String maturityamt;
    private String interestrate;


    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getDepositdate() {
        return depositdate;
    }

    public void setDepositdate(String depositdate) {
        this.depositdate = depositdate;
    }

    public String getMaturitydate() {
        return maturitydate;
    }

    public void setMaturitydate(String maturitydate) {
        this.maturitydate = maturitydate;
    }

    public String getDepositamt() {
        return depositamt;
    }

    public void setDepositamt(String depositamt) {
        this.depositamt = depositamt;
    }

    public String getMaturityamt() {
        return maturityamt;
    }

    public void setMaturityamt(String maturityamt) {
        this.maturityamt = maturityamt;
    }

    public String getInterestrate() {
        return interestrate;
    }

    public void setInterestrate(String interestrate) {
        this.interestrate = interestrate;
    }
}
