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
interface printable {
    int MIN=5;
    void print();
}
public class IntfEx3 implements printable{
    public void print() {
        System.out.println("min " + MIN);
    }
public static void main(String[] args) {
    printable p=new IntfEx3();
    p.print();
    }
}