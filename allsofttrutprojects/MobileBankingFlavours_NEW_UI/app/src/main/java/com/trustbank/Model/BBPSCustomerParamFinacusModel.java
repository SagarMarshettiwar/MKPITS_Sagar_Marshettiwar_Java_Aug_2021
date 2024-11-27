package com.trustbank.Model;

import java.io.Serializable;

public class BBPSCustomerParamFinacusModel implements Serializable {
    String name;
    String value;
    String  min_length;
    String  max_length;
    String  field_type;
    String  is_mandatory;
    String regex;

    public String getParamName() {
        return name;
    }

    public void setParamName(String name) {
        this.name = name;
    }

    public String getMin_length() {
        return min_length;
    }

    public void setMin_length(String min_length) {
        this.min_length = min_length;
    }

    public String getMax_length() {
        return max_length;
    }

    public void setMax_length(String max_length) {
        this.max_length = max_length;
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        this.field_type = field_type;
    }

    public String getIs_mandatory() {
        return is_mandatory;
    }

    public void setIs_mandatory(String is_mandatory) {
        this.is_mandatory = is_mandatory;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

