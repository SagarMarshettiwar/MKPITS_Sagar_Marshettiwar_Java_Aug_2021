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
class Book3
{
    private int id=111;
    private String title="java";

    public int getId() {
    return id;
    }

    public String getTitle() {
    return title;
    }

}
public class EncapsulationTest4 {
    public static void main(String[] args) {
        Book3 b=new Book3();
        System.out.println("Id ="+b.getId());
        System.out.println("Title ="+b.getTitle());
    }
}
