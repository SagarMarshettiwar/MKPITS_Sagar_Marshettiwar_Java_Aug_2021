package com.trustbank.Model;

public class ViewJoinModel
{
    private String joinType,clientId,clientName;


    public ViewJoinModel(String joinType, String clientId, String clientName) {
        this.joinType = joinType;
        this.clientId = clientId;
        this.clientName = clientName;
    }

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
