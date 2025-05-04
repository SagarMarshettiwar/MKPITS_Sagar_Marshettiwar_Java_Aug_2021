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
class Book4{
    private int id;
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void display(){
        System.out.println("Id ="+id);
        System.out.println("name ="+name);
    }
}
public class EncapsulationTest5 {
    public static void main(String[] args) {
        Book4 b=new Book4();
        b.setId(111);
        b.setName("java");
        b.display();
    }
}
