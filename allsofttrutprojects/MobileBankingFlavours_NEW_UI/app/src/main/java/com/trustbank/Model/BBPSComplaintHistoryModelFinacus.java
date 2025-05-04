package com.trustbank.Model;

import java.io.Serializable;

public class BBPSComplaintHistoryModelFinacus implements Serializable {
    private String complaint_type;
    private String participation_type;
    private String txn_ref_id;
    private String agent_id;
    private String biller_id;
    private String disposition;
    private String service_reason;
    private String description;
    private String complaint_id;
    private String complaint_status;

    public BBPSComplaintHistoryModelFinacus(String complaint_type, String participation_type, String txn_ref_id, String agent_id, String biller_id, String disposition, String service_reason, String description, String complaint_id, String complaint_status) {
        this.complaint_type = complaint_type;
        this.participation_type = participation_type;
        this.txn_ref_id = txn_ref_id;
        this.agent_id = agent_id;
        this.biller_id = biller_id;
        this.disposition = disposition;
        this.service_reason = service_reason;
        this.description = description;
        this.complaint_id = complaint_id;
        this.complaint_status = complaint_status;
    }

    public String getComplaint_type() {
        return complaint_type;
    }

    public String getParticipation_type() {
        return participation_type;
    }

    public String getTxn_ref_id() {
        return txn_ref_id;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public String getBiller_id() {
        return biller_id;
    }

    public String getDisposition() {
        return disposition;
    }

    public String getService_reason() {
        return service_reason;
    }

    public String getDescription() {
        return description;
    }

    public String getComplaint_id() {
        return complaint_id;
    }

    public String getComplaint_status() {
        return complaint_status;
    }
}
