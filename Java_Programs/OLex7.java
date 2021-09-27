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
class calculate2
{
    int addition(int n1,int n2)
    {
        return n1+n2;
    }
    float addition(float n1,float n2)
    {
        return n1+n2;
    }
    double addition(double n1,double n2)
    {
        return n1+n2;
    }
}
public class OLex7 {
    public static void main(String[] args) {
        calculate2 cal=new calculate2();
        int res=cal.addition(2,3);
        System.out.println("add of 2 integer no is " + res);
        float res1=cal.addition(2.2f,3.3f);
        System.out.println("add of 2 float no is "+ res1);
        double res2=cal.addition(22.222,32.3);
        System.out.println("add of 2 double no is " + res2);
    }
}