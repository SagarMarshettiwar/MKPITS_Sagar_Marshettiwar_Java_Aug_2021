package com.example.dbhelpertest;

import java.io.Serializable;

public class model implements Serializable {
    int id;
    String Name;
    String Phone;
    public model(String name, String phone) {
        Name = name;
        Phone = phone;
    }
    public model() {
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return Name;
    }
    public String getPhone() {
        return Phone;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        Name = name;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }

}
