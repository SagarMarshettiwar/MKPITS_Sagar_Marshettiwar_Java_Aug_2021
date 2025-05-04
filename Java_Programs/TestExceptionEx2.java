/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EHWMO;

import java.io.IOException;

/**
 *
 * @author SAGAR
 */
class account {
    void deposit(int amt) throws IOException {
        System.out.println("deposit method of account class");
    }
}
class saving extends account {
    void deposit(int amt) throws IOException {
        System.out.println("deposit method of saving class");
    }
}
public class TestExceptionEx2 {
    public static void main(String[] args) {
        try {
            saving s=new saving();
            s.deposit(100);
        }
        catch(Exception ee ) {
            System.out.println(ee);
        }
    }
}
