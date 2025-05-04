package com.example.springdemo;

public class Account {
    Employee eob;

    public Employee getEob() {
        return eob;
    }

    public void setEob(Employee eob) {
        this.eob = eob;
    }
    void transaction(){
        System.out.println("Account "+eob.getId()+" "+eob.getName());
    }
}
