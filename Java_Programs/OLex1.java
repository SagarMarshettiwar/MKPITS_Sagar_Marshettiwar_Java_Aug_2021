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
class Operation{
    void sum(int a,int b){
        System.out.println(a+b);
    }
    void sum(int a,int b,int c){
        System.out.println(a+b+c);
    }
}
public class OLex1 {
    public static void main(String[] args) {
        Operation o=new Operation();
        o.sum(5, 5);
        o.sum(5, 5, 5);
    }   
}
