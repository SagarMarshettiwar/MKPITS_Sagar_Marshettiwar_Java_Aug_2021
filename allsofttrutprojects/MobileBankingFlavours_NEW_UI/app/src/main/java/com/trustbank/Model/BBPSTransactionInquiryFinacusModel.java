package com.trustbank.Model;

import java.io.Serializable;

public class BBPSTransactionInquiryFinacusModel implements Serializable {
    private String tran_Ref_Id;
    private String agent_id;
    private String biller_id;
    private String amount;
    private String tran_date;
    private String status;

    public BBPSTransactionInquiryFinacusModel(String tran_Ref_Id, String agent_id, String biller_id, String amount, String tran_date, String status) {
        this.tran_Ref_Id = tran_Ref_Id;
        this.agent_id = agent_id;
        this.biller_id = biller_id;
        this.amount = amount;
        this.tran_date = tran_date;
        this.status = status;
    }

    public String getTran_Ref_Id() {
        return tran_Ref_Id;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public String getBiller_id() {
        return biller_id;
    }

    public String getAmount() {
        return amount;
    }

    public String getTran_date() {
        return tran_date;
    }

    public String getStatus() {
        return status;
    }
}
