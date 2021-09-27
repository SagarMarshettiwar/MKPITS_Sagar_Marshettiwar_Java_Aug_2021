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
public class OLex10 {
    void sum(int a,int b){
        System.out.println("int arg method invoked");
    }
    void sum(long a,long b){
        System.out.println("long arg method invoked");
    }
    public static void main(String args[]){
        OLex10 obj=new OLex10();
        obj.sum(20,20);
    }
}
