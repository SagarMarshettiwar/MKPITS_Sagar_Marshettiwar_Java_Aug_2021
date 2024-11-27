package com.trustbank.Model;

import java.io.Serializable;

public class NomineeModel implements Serializable
{
    private String srNo;
    private String nomineeId;
    private String name1;
    private String name2;
    private String name3;
    private String nomineeDob;
    private String nomineeAddress;
    private String nomineeCity;
    private String nomineeRelation;
    private String nomineeRelationCode;
    private String nomineePercentage;
    private String NomineeGuarName;
    private String NomineeGuarAdd;
    private String NomineeGuarRelation;
    private String NomineeGuarRelationCode;

    public NomineeModel()
    {}

    public NomineeModel(String srNo, String nomineeId, String name1, String name2, String name3, String nomineeDob, String nomineeAddress, String nomineeCity, String nomineeRelation, String nomineeRelationCode, String nomineePercentage, String nomineeGuarName, String nomineeGuarAdd, String nomineeGuarRelation, String nomineeGuarRelationCode) {
        this.srNo = srNo;
        this.nomineeId = nomineeId;
        this.name1 = name1;
        this.name2 = name2;
        this.name3 = name3;
        this.nomineeDob = nomineeDob;
        this.nomineeAddress = nomineeAddress;
        this.nomineeCity = nomineeCity;
        this.nomineeRelation = nomineeRelation;
        this.nomineeRelationCode = nomineeRelationCode;
        this.nomineePercentage = nomineePercentage;
        this.NomineeGuarName = nomineeGuarName;
        this.NomineeGuarAdd = nomineeGuarAdd;
        this.NomineeGuarRelation = nomineeGuarRelation;
        this.NomineeGuarRelationCode = nomineeGuarRelationCode;
    }

    public String getSrNo() {
        return srNo;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public String getNomineeId() {
        return nomineeId;
    }

    public void setNomineeId(String nomineeId) {
        this.nomineeId = nomineeId;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public String getNomineeDob() {
        return nomineeDob;
    }

    public void setNomineeDob(String nomineeDob) {
        this.nomineeDob = nomineeDob;
    }

    public String getNomineeAddress() {
        return nomineeAddress;
    }

    public void setNomineeAddress(String nomineeAddress) {
        this.nomineeAddress = nomineeAddress;
    }

    public String getNomineeCity() {
        return nomineeCity;
    }

    public void setNomineeCity(String nomineeCity) {
        this.nomineeCity = nomineeCity;
    }

    public String getNomineeRelation() {
        return nomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }

    public String getNomineePercentage() {
        return nomineePercentage;
    }

    public void setNomineePercentage(String nomineePercentage) {
        this.nomineePercentage = nomineePercentage;
    }

    public String getNomineeRelationCode() {
        return nomineeRelationCode;
    }

    public void setNomineeRelationCode(String nomineeRelationCode) {
        this.nomineeRelationCode = nomineeRelationCode;
    }

    public String getNomineeGuarName() {
        return NomineeGuarName;
    }

    public void setNomineeGuarName(String nomineeGuarName) {
        NomineeGuarName = nomineeGuarName;
    }

    public String getNomineeGuarAdd() {
        return NomineeGuarAdd;
    }

    public void setNomineeGuarAdd(String nomineeGuarAdd) {
        NomineeGuarAdd = nomineeGuarAdd;
    }

    public String getNomineeGuarRelation() {
        return NomineeGuarRelation;
    }

    public void setNomineeGuarRelation(String nomineeGuarRelation) {
        NomineeGuarRelation = nomineeGuarRelation;
    }

    public String getNomineeGuarRelationCode() {
        return NomineeGuarRelationCode;
    }

    public void setNomineeGuarRelationCode(String nomineeGuarRelationCode) {
        NomineeGuarRelationCode = nomineeGuarRelationCode;
    }
}
