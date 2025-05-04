package com.trustbank.Model;

public class SIModel {
    private String ScheduleExecutionDate;
    private String LoanAmount;

    public String getScheduleExecutionDate() {
        return ScheduleExecutionDate;
    }

    public void setScheduleExecutionDate(String scheduleExecutionDate) {
        ScheduleExecutionDate = scheduleExecutionDate;
    }

    public String getLoanAmount() {
        return LoanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        LoanAmount = loanAmount;
    }
}
