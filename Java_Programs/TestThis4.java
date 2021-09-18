package ThisKeywordExample;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SAGAR
 */
class A{  
void m(){
    System.out.println("hello m");
}  
void n(){  
    System.out.println("hello n");  
    //m();//same as this.m()  
    this.m();  
    }  
}  
public class TestThis4{  
    public static void main(String args[]){  
    A a=new A();  
    a.n();  
    }
}
