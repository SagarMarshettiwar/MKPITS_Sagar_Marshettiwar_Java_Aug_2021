package com.example.springdemo;

public class Employee {
    public int id;
    public String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    void Display(){
            System.out.println("hello Employee "+getName()+" "+getId());
        }
}
