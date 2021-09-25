/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperKeyWordExamples;

/**
 *
 * @author SAGAR
 */
class Animal2{
    void eat(){System.out.println("eating...");}
}
class Dog2 extends Animal2{
    void eat(){
        System.out.println("eating bread...");}
    void bark(){
        System.out.println("barking...");}
    void work()
    {
        super.eat();
        bark();
    }
}
public class SuperEx7 {
    public static void main(String args[]){
        Dog2 d=new Dog2();
        d.work();
    }
}