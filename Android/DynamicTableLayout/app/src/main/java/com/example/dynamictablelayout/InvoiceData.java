package com.example.dynamictablelayout;

import java.math.BigDecimal;
import java.util.Date;
public class InvoiceData {
    public int id;
    public int invoiceNumber;
    public Date invoiceDate;
    public String customerName;
    public String customerAddress;
    public String invoiceAmount;
    public String amountDue;

    public InvoiceData(int id, int invoiceNumber, Date invoiceDate, String customerName, String customerAddress, String invoiceAmount, String amountDue) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.invoiceAmount = invoiceAmount;
        this.amountDue = amountDue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(String invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(String amountDue) {
        this.amountDue = amountDue;
    }
}