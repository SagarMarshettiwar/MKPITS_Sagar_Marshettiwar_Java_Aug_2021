/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StaticExample;

/**
 *
 * @author SAGAR
 */
public class Counter{  
int count=0;//will get memory each time when the instance is created  
  
Counter(){  
    count++;//incrementing value  
    System.out.println(count);  
}  
  
public static void main(String args[]){  
//Creating objects  
    Counter c1=new Counter();  
    Counter c2=new Counter();  
    Counter c3=new Counter();  
    }  
}  
