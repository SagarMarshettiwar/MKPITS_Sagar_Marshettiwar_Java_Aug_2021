/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NestedTry;

/**
 *
 * @author SAGAR
 */
public class NestedTryEx1 {
    public static void main(String[] args) {
        try{
            try{
                int a=50/0;
            }catch(ArithmeticException e){
                System.out.println(e);
            }
            try{
                int a[]=new int [5];
                a[5]=6;
            }catch(ArrayIndexOutOfBoundsException e){
                System.out.println(e);
            }
                
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println("normal flow");
    }
}