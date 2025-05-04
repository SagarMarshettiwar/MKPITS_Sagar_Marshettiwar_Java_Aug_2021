/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Throw;
//CHECK EXCEPTION

import java.io.IOException;

/**
 *
 * @author SAGAR
 */
public class ThrowEx1 {
    static void validate(int age)throws Exception{
        if(age<18){
            throw new IOException("not valid");
        }else{
            System.out.println("welcome");
        }
    }
    public static void main(String[] args){
        try{
            validate(13);
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println("rest of code");
    }
}