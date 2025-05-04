/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Throw;
//UNCHECK EXCEPTION
/**
 *
 * @author SAGAR
 */
public class ThrowEx2 {
    static void validate(int age){
        if(age<18){
            throw new ArithmeticException("not valid");
        }else{
            System.out.println("welcome");
        }
    }
    public static void main(String[] args){
            validate(13);
            System.out.println("rest of code");
    }
}