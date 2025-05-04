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
public class TryCatchEx11 {
    public static void main(String[] args){
        try{
            String s=null;
            System.out.println(s.length());
        }catch(Exception e){
            System.out.println(e);
        }
        finally{
            System.out.println("final");
        }
        System.out.println("hi");
    }
}