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
class Books{
    private String title;
    private String author;
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
public class EncapsulationTest2 {
    public static void main(String[] args) {
        Books b=new Books();
        b.setTitle("java");
        System.out.println("Title ="+b.getTitle());
        b.setAuthor("James");
        System.out.println("Author ="+b.getAuthor());
    }
}
