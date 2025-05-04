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
class Adder1{
    static int add(int a, int b){
        return a+b;
    }
    static double add(double a, double b){
        return a+b;
    }
}
class OLex6{
    public static void main(String[] args){
        System.out.println(Adder1.add(11,11));
        System.out.println(Adder1.add(12.3,12.6));
    }
}
