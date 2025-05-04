/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OverRidingExamples;

/**ex : 3 create a class account having field accountno , bal and method deposit and withdrawl.
create a subclass saving and override the deposit and withdrawl method.
 *
 * @author SAGAR
 */
class account {
    int acctno;
    int bal=1000;
    void deposit(int amt) {
        System.out.println("deposit method of account class");
    }
    void withdrawl(int amt) {
        System.out.println("withdrawl method of account class");
    }
}
class saving extends account {
    void deposit(int amt) {
        bal=bal+amt;
        System.out.println("deposit method of saving class class,balance is " + bal);
    }
    void withdrawl(int amt) {
        bal=bal-amt;
        System.out.println("withdrawl method of saving class class,balance is " + bal);
    }
}
public class ORex3 {
    public static void main(String[] args) {
        //account act=new account();
        // act.acctno=123;
        // act.deposit(100);
        // act.withdrawl(50);
        saving s=new saving();
        s.acctno=123;
        s.deposit(100);
        s.withdrawl(50);
    }
}
