package com.trustbank.Model;

import java.io.Serializable;

public class BBPSTransactionHistoryFinacusModel implements Serializable {
    private String biller_name;
    private String bill_date;
    private String biller_id;
    private String payment_date;
    private String amount;
    private String conv_fees;
    private String payment_mode;
    private String txn_ref_id;
    private String ref_id;
    private String rrn;
    private String response_code;

    public BBPSTransactionHistoryFinacusModel(String biller_name, String bill_date, String biller_id, String payment_date, String amount, String conv_fees, String payment_mode, String txn_ref_id, String ref_id, String rrn, String response_code) {
        this.biller_name = biller_name;
        this.bill_date = bill_date;
        this.biller_id = biller_id;
        this.payment_date = payment_date;
        this.amount = amount;
        this.conv_fees = conv_fees;
        this.payment_mode = payment_mode;
        this.txn_ref_id = txn_ref_id;
        this.ref_id = ref_id;
        this.rrn = rrn;
        this.response_code = response_code;
    }

    public String getBiller_name() {
        return biller_name;
    }

    public String getBill_date() {
        return bill_date;
    }

    public String getBiller_id() {
        return biller_id;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public String getAmount() {
        return amount;
    }

    public String getConv_fees() {
        return conv_fees;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public String getTxn_ref_id() {
        return txn_ref_id;
    }

    public String getRef_id() {
        return ref_id;
    }

    public String getRrn() {
        return rrn;
    }

    public String getResponse_code() {
        return response_code;
    }
}
