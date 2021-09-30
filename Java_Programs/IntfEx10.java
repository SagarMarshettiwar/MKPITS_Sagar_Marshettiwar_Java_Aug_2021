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
interface Printable1{
    void print();
}
interface Showable1 extends Printable1{
    void show();
}
public class IntfEx10 implements Showable1{
    public void print(){
        System.out.println("Hello");
    }
    public void show(){
        System.out.println("Welcome");
    }
    public static void main(String args[]){
        IntfEx10 obj = new IntfEx10();
        obj.print();
        obj.show();
        Showable1 s=new IntfEx10();
        s.show();
    }
}
