package com.trustbank.Model;

import android.os.Parcelable;

import java.io.Serializable;

public class OldJoinListModel implements Serializable {
    String clientId,clientName;

    public OldJoinListModel(String clientId, String clientName) {
        this.clientId = clientId;
        this.clientName = clientName;
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
