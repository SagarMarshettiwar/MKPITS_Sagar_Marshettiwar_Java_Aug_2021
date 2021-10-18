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
public class NestedTryEx2 {
    public static void main(String[] args) {
        try{
            try{
                try{
                    int arr[]={1,2,3,4};
                    System.out.println(arr[10]);
                }catch(ArithmeticException e){
                    System.out.println(e);
                }
            }catch(ArithmeticException e){
                System.out.println(e);
            }
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println(e);
        }
    }
}
