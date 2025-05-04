package com.example.springdemo2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Account3 {
    Employee3 emp3;

    public Employee3 getEmp3() {
        return emp3;
    }
    @Autowired
    @Qualifier("merawala")
    public void setEmp3(Employee3 emp3) {
        this.emp3 = emp3;
    }
    void Ashow(){
        System.out.println(emp3.getId()+" "+emp3.getName());
    }
}
