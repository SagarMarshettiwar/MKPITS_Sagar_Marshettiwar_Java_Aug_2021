/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EncapsulationExample;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
class Books1{
    private int id;
    private String title;
    private String author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id>100){
            this.id=id;
        }else{
            System.out.println("Id should greater than 100");
        }
    }
    
    public void setTitle(String title){
        this.title=title;
    }
    public String getTitle(){
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
}
public class EncapsulationTest3 {
    public static void main(String[] args) {
        Books1 b=new Books1();
        Scanner s=new Scanner(System.in);
        System.out.println("Enter id");
        int num=s.nextInt();
        b.setId(num);
        System.out.println("Id ="+b.getId());
        b.setTitle("java");
        System.out.println("Title ="+b.getTitle());
        b.setAuthor("James");
        System.out.println("Author ="+b.getAuthor());
    }
}
