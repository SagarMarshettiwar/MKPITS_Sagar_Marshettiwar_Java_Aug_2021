package com.trustbank.Model;

import java.util.ArrayList;
import java.util.List;

public class AccountOverviewModel {

    private int image;
    private String text;

    List<String> spinnerItems;
    public AccountOverviewModel(int image, String text, List<String> spinnerItems) {

        this.image = image;
        this.text = text;
        this.spinnerItems = spinnerItems;
    }

    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public List<String> getSpinnerItems(){
        return spinnerItems;
    }
}
