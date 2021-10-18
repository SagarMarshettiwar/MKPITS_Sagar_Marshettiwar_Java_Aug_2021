/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EHWMO;

/**
 *
 * @author SAGAR
 */
class Parent{
    void msg(){
        System.out.println("parent method");
    }
}
public class TestException extends Parent{
    void msg() throws ArithmeticException {
        System.out.println("child");
    }
    public static void main(String[] args) {
        Parent p=new TestException();
        p.msg();
    }
}
