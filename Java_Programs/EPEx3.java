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
public class EPEx3 {
    void m2(){  
        int data=50/0;  
    }  
    void n2(){
        System.out.println("n");
         m2();  
    }  
    void p2(){
        System.out.println("p");
        n2();
    }
    public static void main(String args[]){  
        EPEx3 obj1=new EPEx3();
        try{
            obj1.p2();
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println("normal flow...");  
   }
}