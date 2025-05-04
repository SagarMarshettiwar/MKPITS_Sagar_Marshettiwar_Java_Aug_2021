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
class calculate1
{
    int addition(int n1,int n2)
    {
        return n1+n2;
    }
    int addition(int n1,int n2,int n3)
    {
        return n1+n2+n3;
    }
}
public class OLex4 {
    public static void main(String[] args) {
        calculate1 cal=new calculate1();
        int res=cal.addition(2,3);
        System.out.println("add of 2 no is " +res);
         res=cal.addition(2,3,4);
        System.out.println("add of 3 no is " +res);
    }
}
