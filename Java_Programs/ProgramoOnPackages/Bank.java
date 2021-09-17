/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProgramoOnPackages;
import Hello.Account;
/**
 *
 * @author SAGAR
 */
public class Bank {
    public static void main(String[] args) {
        Account a=new Account();
        int result=a.deposit(100);
        System.out.println("Ans = "+result);
    }
}
