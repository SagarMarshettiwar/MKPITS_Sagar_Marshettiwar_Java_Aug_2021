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
class Adder{
    static int add(int a,int b) {
        return a+b;
    }
    static int add(int a,int b,int c) {
        return a+b+c;
    }
}
public class OLex3{
    public static void main(String[] args){
        System.out.println(Adder.add(11,11));
        System.out.println(Adder.add(11,11,11));
    }
}