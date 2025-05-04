/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OverRidingExamples;

/**
 *
 * @author SAGAR
 */
class account1 {
    int actno;
    int bal=1000;
    String deposit(int actno,int amt)
    {
        return "account class deposit method";
    }
    String withdrawl(int actno,int amt)
    {
        return "account class withdrawl method";
    }
}
class saving1 extends account1 {
    int interest=500;
    String deposit(int actno,int amt)
    {
        this.actno=actno;
        bal=bal+amt+interest;
        return "account deposited for acct no " +actno + ", bal is " + bal;
    }
    String withdrawl(int actno,int amt)
    {
        this.actno=actno;
        if(amt > bal)
        {
            return "insufficient fund ";
        }else{
            bal = bal - amt;
            return "account withdrawl for acct no "+ actno + ", bal is " + bal;
        }
    }
}
public class ORex4 {
    public static void main(String[] args) {
        java.util.Scanner s=new java.util.Scanner(System.in);
        System.out.println("enter account no");
        int actno=s.nextInt();
        System.out.println("enter amount");
        int amt=s.nextInt();
        System.out.println("do you want to deposit or withdrawl");
        String ans=s.next();
        saving1 act = new saving1();
        String str;
        if(ans.equals("deposit")) {
            str = act.deposit(actno,amt);
            System.out.println(str);
        }else if(ans.equals("withdrawl")){
            str = act.withdrawl(actno,amt);
            System.out.println(str);
        }
    }
}
