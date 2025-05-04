/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractionExamples;

/**
 *
 * @author SAGAR
 */
abstract class Bike{
    public int a;
    void aval(){
        System.out.println(a);
    }
    Bike(){
       System.out.println("bike is created");
    }  
    abstract void run();  
    void changeGear(){
        System.out.println("gear changed");
    }     
}   
 class Honda extends Bike{     
     void run(){
        System.out.println("running safely..");
    }
}  
public class AbstractEx2 {
    public static void main(String args[]){  
        Bike obj = new Honda();
        obj.run();  
        obj.changeGear();
        obj.a=5;
        obj.aval();
    }
}
