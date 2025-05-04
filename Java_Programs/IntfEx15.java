/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceExamples;
/**
 *
 * @author SAGAR
 */
interface A1{
    void a();//bydefault, public and abstract
    void b();
    void c();
    void d();
}
//Creating abstract class that provides the implementation of one method of A interface
abstract class B1 implements A1{
    public void c(){
        System.out.println("I am C");
    }
}
//Creating subclass of abstract class, now we need to provide the implementation of rest of the methods
class M1 extends B1{
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
//Creating a test class that calls the methods of A interface
public class IntfEx15{
    public static void main(String args[]){
        A1 a=new M1();
        a.a();
        a.b();
        a.c();
        a.d();
    }
}