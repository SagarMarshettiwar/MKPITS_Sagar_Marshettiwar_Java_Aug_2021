package com.example.springdemo2;

public class Employee2 {
    public int id2;
    public String name2;

    public Employee2(int id2, String name2) {
        this.id2 = id2;
        this.name2 = name2;
    }

    public int getId2() {
        return id2;
    }

    public String getName2() {
        return name2;
    }

    public void showdetail(){
        System.out.println(" Emp2 "+id2+""+name2);
    }
}
