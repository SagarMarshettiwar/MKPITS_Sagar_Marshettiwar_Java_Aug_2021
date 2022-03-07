package com.example.springdemo;

public class Account1 {
    Employee1 eob1;

    public Employee1 getEob1() {
        return eob1;
    }

    public void setEob1(Employee1 eob1) {
        this.eob1 = eob1;
    }

    void tran(){
        System.out.println("Account "+eob1.getId1()+" "+eob1.getName1());
    }
}
