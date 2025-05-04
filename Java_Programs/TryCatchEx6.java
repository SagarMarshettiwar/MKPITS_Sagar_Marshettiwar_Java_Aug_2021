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
public class TryCatchEx6 {
    public static void main(String[] args) {
        try{
            int data=50/0;
        }catch(Exception e){
            int data1=50/0;
        }
        System.out.println("rest");
    }
}
