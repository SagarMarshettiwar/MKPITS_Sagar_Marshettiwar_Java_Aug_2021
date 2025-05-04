/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EHWMO;

/**
 *
 * @author SAGAR
 */
class Parent1{    
  void msg1() throws Exception {  
    System.out.println("parent method");  
  }    
}    
public class TestException1 extends Parent1 {
    void msg1()throws Exception {  
    System.out.println("child method");  
  }    
    
  public static void main(String args[]){    
   Parent1 p = new TestException1();    
     
   try {    
   p.msg1();    
   }  
   catch(Exception e) {}    
  }
}
