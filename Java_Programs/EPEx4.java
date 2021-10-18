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
public class EPEx4 {
    void m3(){
        System.out.println("m");
        try{
            int data=50/0;
        }catch(ArithmeticException e){
            System.out.println("method");
        }    
    }  
    void n3(){
        System.out.println("n");
         m3();  
    }  
    void p3(){
        System.out.println("p");
        n3();
    }
    public static void main(String args[]){  
        EPEx4 obj=new EPEx4();
        try{
            obj.p3();  
        }catch(Exception e){
            System.out.println("main");
        }
        System.out.println("normal flow...");  
   }
}