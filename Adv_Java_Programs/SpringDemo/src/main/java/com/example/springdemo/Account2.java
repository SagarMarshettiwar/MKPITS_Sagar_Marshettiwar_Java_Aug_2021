package com.example.springdemo;

public class Account2 {
    Employee2 Emp2;

    public Employee2 getEmp2() {
        return Emp2;
    }

    public void setEmp2(Employee2 emp2) {
        Emp2 = emp2;
    }

    void tran1(){
        System.out.println("Account2 "+Emp2.getId2()+" "+Emp2.getName2());
    }
}