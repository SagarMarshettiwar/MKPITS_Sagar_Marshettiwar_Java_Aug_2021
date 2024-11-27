package com.trustbank.Model;

import java.io.Serializable;
import java.util.HashMap;

public class BBPSBillDetailFinacusModel implements Serializable {

    private String transaction_ref_id;
    private String payment_ref_id;
    private String customer_name;
    private String customer_mobile_no;
    private String consumer_number;
    private String agent_id;
    private String biller_category;
    private String billerId;
    private String biller_name;
    private String bill_number;
    private String bill_period;
    private String bill_date;
    private String bill_due_date;
    private String bill_amount;
    private String payment_channel;
    private String payment_mode;
    private String payment_status;
    private String conv_fees;
    private String total_bill_amount;
    private String bill_pay_date_time;
    HashMap<String, String> custParam;

    public BBPSBillDetailFinacusModel(String transaction_ref_id, String payment_ref_id, String customer_name, String customer_mobile_no, String consumer_number, String agent_id, String biller_category, String billerId, String biller_name, String bill_number, String bill_period, String bill_date, String bill_due_date, String bill_amount, String payment_channel, String payment_mode, String payment_status, String conv_fees, String total_bill_amount, String bill_pay_date_time, HashMap<String, String> custParam) {
        this.transaction_ref_id = transaction_ref_id;
        this.payment_ref_id = payment_ref_id;
        this.customer_name = customer_name;
        this.customer_mobile_no = customer_mobile_no;
        this.consumer_number = consumer_number;
        this.agent_id = agent_id;
        this.biller_category = biller_category;
        this.billerId = billerId;
        this.biller_name = biller_name;
        this.bill_number = bill_number;
        this.bill_period = bill_period;
        this.bill_date = bill_date;
        this.bill_due_date = bill_due_date;
        this.bill_amount = bill_amount;
        this.payment_channel = payment_channel;
        this.payment_mode = payment_mode;
        this.payment_status = payment_status;
        this.conv_fees = conv_fees;
        this.total_bill_amount = total_bill_amount;
        this.bill_pay_date_time = bill_pay_date_time;
        this.custParam =custParam;
    }

    public HashMap<String, String> getCustParam() {
        return custParam;
    }

    public String getTransaction_ref_id() {
        return transaction_ref_id;
    }


    public String getPayment_ref_id() {
        return payment_ref_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public String getCustomer_mobile_no() {
        return customer_mobile_no;
    }

    public void setCustomer_mobile_no(String customer_mobile_no) {
        this.customer_mobile_no = customer_mobile_no;
    }

    public String getConsumer_number() {
        return consumer_number;
    }

    public void setConsumer_number(String consumer_number) {
        this.consumer_number = consumer_number;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getBiller_category() {
        return biller_category;
    }

    public void setBiller_category(String biller_category) {
        this.biller_category = biller_category;
    }

    public String getBillerId() {
        return billerId;
    }

    public void setBillerId(String billerId) {
        this.billerId = billerId;
    }

    public String getBiller_name() {
        return biller_name;
    }

    public void setBiller_name(String biller_name) {
        this.biller_name = biller_name;
    }

    public String getBill_number() {
        return bill_number;
    }

    public void setBill_number(String bill_number) {
        this.bill_number = bill_number;
    }

    public String getBill_period() {
        return bill_period;
    }

    public void setBill_period(String bill_period) {
        this.bill_period = bill_period;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public String getBill_due_date() {
        return bill_due_date;
    }

    public void setBill_due_date(String bill_due_date) {
        this.bill_due_date = bill_due_date;
    }

    public String getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(String bill_amount) {
        this.bill_amount = bill_amount;
    }

    public String getPayment_channel() {
        return payment_channel;
    }

    public void setPayment_channel(String payment_channel) {
        this.payment_channel = payment_channel;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getConv_fees() {
        return conv_fees;
    }

    public void setConv_fees(String conv_fees) {
        this.conv_fees = conv_fees;
    }

    public String getTotal_bill_amount() {
        return total_bill_amount;
    }

    public void setTotal_bill_amount(String total_bill_amount) {
        this.total_bill_amount = total_bill_amount;
    }

    public String getBill_pay_date_time() {
        return bill_pay_date_time;
    }

    public void setBill_pay_date_time(String bill_pay_date_time) {
        this.bill_pay_date_time = bill_pay_date_time;
    }
}
