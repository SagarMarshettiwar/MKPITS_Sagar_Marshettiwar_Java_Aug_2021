/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceExamples;

/**
 *
 * @author SAGAR
 */
interface Printable{
    void print();
}
interface Showable{
    void show();
}
public class IntfEx8 implements Printable,Showable{
    public void print(){
        System.out.println("hello");
    }
    public void show(){
        System.out.println("Welcome");
    }
    public static void main(String[] args) {
        IntfEx8 i=new IntfEx8();
        i.print();
        i.show();
    }
}
