/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OverloadingExamples;

/**
 *
 * @author SAGAR
 */
class calculate {
    void add(int n1,int n2) {
        int res=n1+n2;
        System.out.println("addition of 2 no is " + res);
    }
    void add(int n1,int n2,int n3) {
        int res=n1+n2+n3;
        System.out.println("addition of 3 no is " + res);
    }
}
public class OLex2 {
    public static void main(String[] arg) {
        calculate c1=new calculate();
        c1.add(2,3);
        c1.add(2,3,5);
    }
}
