/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OverRidingExamples;

import java.util.Scanner;

/**ex 4 create a class account having field accountno , bal and method deposit and withdrawl.
create a subclass saving inherited from account class and override the deposit(with interest) and
withdrawl method.
create a subclass current inherited from account and override the deposit(without interest) and
withdrawl method.
 *
 * @author SAGAR
 */
class account2{
    int actno;
    int bal=1000;
    String deposit(int actno,int amt)
    {
        return "account class deposit method";
    }
    String withdrawl(int actno,int amt)
    {
        this.actno=actno;
        if(amt > bal){
            return "insufficient fund ";
        }else{
            bal = bal - amt;
            return "account withdrawl for acct no "+ actno + ", bal is " + bal;
        }
    }
}
class saving2 extends account2 {
    int interest=500;
    String deposit(int actno,int amt)
    {
        this.actno=actno;
        bal=bal+amt+interest;
        return "account deposited for acct no " +actno + ", bal is " + bal;
    }
}
class current extends account2 {
    String deposit(int actno,int amt)
    {
        this.actno=actno;
        bal=bal+amt;
        return "account deposited for acct no " +actno + ", bal is " + bal;
    }
}
public class ORex5 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("enter account no");
        int actno=s.nextInt();
        System.out.println("enter amount");
        int amt=s.nextInt();
        System.out.println("enter saving or current acct type");
        String acttype=s.next();
        System.out.println("do you want to deposit or withdrawl");
        String ans=s.next();
        saving2 sav=new saving2();
        current cur=new current();
        String str;
        if(acttype.equals("saving"))
        {   
            if(ans.equals("deposit")){
            str = sav.deposit(actno,amt);
            System.out.println(str);
            }else if (ans.equals("withdrawl")){
            str = sav.withdrawl(actno,amt);
            System.out.println(str);
            }
        }else if(acttype.equals("current")){
            if(ans.equals("deposit")) {
                str = cur.deposit(actno,amt);
                System.out.println(str);
            }else if (ans.equals("withdrawl")){
                str = cur.withdrawl(actno,amt);
                System.out.println(str);
            }
        }
    }
}

