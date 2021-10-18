/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionPropogation;

/**
 *
 * @author SAGAR
 */
public class EPEx2 {
    void m1(){  
        int data=50/0;  
    }  
    void n1(){
        System.out.println("n");
         m1();  
    }  
    void p1(){
        System.out.println("p");
        try{  
            n1();  
        }catch(Exception e){
            System.out.println(e);
        }  
    }  
    public static void main(String args[]){  
        EPEx2 obj=new EPEx2();  
        obj.p1();  
        System.out.println("normal flow...");  
   }  
}