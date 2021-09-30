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
interface A{
    void print();
}
interface B{
    void print();
}
public class IntfEx9 implements A,B{
    public void print(){
        System.out.println("hi");
    }
    public static void main(String[] args) {
        IntfEx9 i=new IntfEx9();
        i.print();
    }
}
