/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EncapsulationExample;

/**
 *
 * @author SAGAR
 */
class Student{
    private int id;
    private String name;
    private String clg;

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

    public String getClg() {
        return clg;
    }

    public void setClg(String clg) {
        this.clg = clg;
    }
    
}
public class EncapsulationTest1 {
    public static void main(String[] args) {
        Student s=new Student();
        s.setId(123);
        s.setName("Sagar");
        s.setClg("SB.Jain");
        System.out.println("Id ="+s.getId()+"\nName ="+s.getName()+"\nCollege ="+s.getClg());
    }
}
