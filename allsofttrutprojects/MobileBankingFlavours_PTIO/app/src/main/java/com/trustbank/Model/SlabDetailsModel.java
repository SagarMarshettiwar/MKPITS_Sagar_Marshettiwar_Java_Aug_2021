package com.trustbank.Model;

import java.util.List;

public class SlabDetailsModel {
    private String acno;
    private String balance;
    private String schemeName;
    private String minimumbalance;
    private String maxtranbalanceamount;

    private List<SavingAccLevelModel> slablevels;

    public String getAcno() {
        return acno;
    }
    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getMinimumbalance() {
        return minimumbalance;
    }

    public void setMinimumbalance(String minimumbalance) {
        this.minimumbalance = minimumbalance;
    }

    public String getMaxtranbalanceamount() {
        return maxtranbalanceamount;
    }

    public void setMaxtranbalanceamount(String maxtranbalanceamount) {
        this.maxtranbalanceamount = maxtranbalanceamount;
    }

    public List<SavingAccLevelModel> getSlablevels() {
        return slablevels;
    }

    public void setSlablevels(List<SavingAccLevelModel> slablevels) {
        this.slablevels = slablevels;
    }
}
