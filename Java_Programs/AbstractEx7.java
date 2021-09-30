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
interface A{  
void a();  
void b();  
void c();  
void d();  
}  
abstract class B implements A{
    abstract void ho();
    public void c(){
        System.out.println("I am c");
    }  
}  
class M extends B{
    void ho(){
        System.out.println("hi");
    }
    public void a(){
        System.out.println("I am a");
    }  
    public void b(){
        System.out.println("I am b");
    }  
    public void d(){
    System.out.println("I am d");
    }  
}
public class AbstractEx7 {
    public static void main(String args[]){  
        B b=new M();
        System.out.println("Abs");
        b.a();
        b.b();
        b.c();
        b.d();
        b.ho();
        A a=new M();
        System.out.println("interf");
        a.a();  
        a.b();  
        a.c();  
        a.d();  
    }
}
