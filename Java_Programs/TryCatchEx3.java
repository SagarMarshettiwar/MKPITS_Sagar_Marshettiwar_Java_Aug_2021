/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TryCatch;

/**
 *
 * @author SAGAR
 */
public class TryCatchEx3 {
    public static void main(String[] args) {
        try{
            int a=50/0;
        }catch(ArithmeticException e){
            System.out.println(e);
        }
        
    }
}
