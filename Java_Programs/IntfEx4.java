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
interface printable1{
    void print();
}
public class IntfEx4 implements printable1{
    public void print(){
        System.out.println("Hello");
    }
public static void main(String args[]){
    IntfEx4 obj = new IntfEx4();
    obj.print();
    }
}
