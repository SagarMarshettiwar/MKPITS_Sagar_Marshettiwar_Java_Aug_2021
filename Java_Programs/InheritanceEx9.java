/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InheritanceExamples;

/**
 *
 * @author SAGAR
 */
class account {
    int acctno;
    int balance;
    void insertaccountdata(int acctno,int balance)
    {
        this.acctno=acctno;
        this.balance=balance;
    }
    void showbalance()
    {
        System.out.println("balance for accuntno " + acctno + " is " + balance);
    }
}
class saving extends account
{
    void deposit(int amount)
    {
        int interest=500;
        balance=balance + amount + interest;
        System.out.println("amount deposited successfully in saving account");
    }
}
class current extends account 
{
    void deposit(int amount)
    {
        balance=balance + amount ;
        System.out.println("amount deposited successfully in current account");
    }
}
public class InheritanceEx9 {
    public static void main(String[] arg) {
        saving s=new saving();
        s.insertaccountdata(123,1000);
        s.deposit(200);
        s.showbalance();

        current c=new current();
        c.insertaccountdata(223,10000);
        c.deposit(1000);
        c.showbalance();
    }
}

