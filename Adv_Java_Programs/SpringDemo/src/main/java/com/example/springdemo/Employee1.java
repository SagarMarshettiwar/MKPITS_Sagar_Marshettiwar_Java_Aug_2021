package com.example.springdemo;

public class Employee1 {
    public int id1;
    public String name1;

    public Employee1(int id1, String name1) {
        this.id1 = id1;
        this.name1 = name1;
    }

    public int getId1() {
        return id1;
    }

    public String getName1() {
        return name1;
    }

    public void show(){
        System.out.println(" Emp "+id1+""+name1);
    }
}
