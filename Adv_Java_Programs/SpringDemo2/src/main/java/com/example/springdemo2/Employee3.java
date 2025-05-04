package com.example.springdemo2;

public class Employee3 {
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
    void display(){
        System.out.println(getName()+" "+getId());
    }
}
