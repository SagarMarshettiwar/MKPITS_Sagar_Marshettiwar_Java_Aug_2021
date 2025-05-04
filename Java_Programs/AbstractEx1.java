/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractionExamples;

/**
 *
 * @author SAGAR
 */
abstract class Account{
    abstract void deposit();
    void Withdrawl(){
        System.out.println("hi");
    }
}
class Saving extends Account{
    void deposit(){
        System.out.println("hello");
    }
}
public class AbstractEx1 {
    public static void main(String[] args) {
        Account a=new Saving();
        a.deposit();
        a.Withdrawl();
    }
}
