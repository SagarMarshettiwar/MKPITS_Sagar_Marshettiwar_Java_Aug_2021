package com.trustbank.Model;

public class FdSchemeModel {
    private String fdSchemeId,fdSchemeName,fdTransferToAccount;

    public FdSchemeModel(String fdSchemeId, String fdSchemeName, String fdTransferToAccount) {
        this.fdSchemeId = fdSchemeId;
        this.fdSchemeName = fdSchemeName;
        this.fdTransferToAccount = fdTransferToAccount;
    }

    public String getFdSchemeId() {
        return fdSchemeId;
    }

    public void setFdSchemeId(String fdSchemeId) {
        this.fdSchemeId = fdSchemeId;
    }

    public String getFdSchemeName() {
        return fdSchemeName;
    }

    public void setFdSchemeName(String fdSchemeName) {
        this.fdSchemeName = fdSchemeName;
    }

    public String getFdTransferToAccount() {
        return fdTransferToAccount;
    }

    public void setFdTransferToAccount(String fdTransferToAccount) {
        this.fdTransferToAccount = fdTransferToAccount;
    }
}
