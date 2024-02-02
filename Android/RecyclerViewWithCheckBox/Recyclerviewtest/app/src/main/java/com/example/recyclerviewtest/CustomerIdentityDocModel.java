package com.example.recyclerviewtest;

public class CustomerIdentityDocModel {

    private String idDocId;
    private String idDoc;
    private String docrefNo;
    private String issuingAuth;
    private String expiryDate;
    private String ckname;

    public String getCkname() {
        return ckname;
    }

    public void setCkname(String ckname) {
        this.ckname = ckname;
    }

    public String getIdDocId() {
        return idDocId;
    }

    public void setIdDocId(String idDocId) {
        this.idDocId = idDocId;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public String getDocrefNo() {
        return docrefNo;
    }

    public void setDocrefNo(String docrefNo) {
        this.docrefNo = docrefNo;
    }

    public String getIssuingAuth() {
        return issuingAuth;
    }

    public void setIssuingAuth(String issuingAuth) {
        this.issuingAuth = issuingAuth;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
