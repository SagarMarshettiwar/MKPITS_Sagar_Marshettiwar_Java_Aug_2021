package com.trustbank.Model;

public class CheckBoxAccountOverviewModel {

    private String text;
    private boolean isSelected;

    public CheckBoxAccountOverviewModel(String text,boolean isSelected) {
        this.text = text;
        this.isSelected=isSelected;
    }

    public String getText() {
        return text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setChecked(boolean checked){
        isSelected = checked;
    }
}
