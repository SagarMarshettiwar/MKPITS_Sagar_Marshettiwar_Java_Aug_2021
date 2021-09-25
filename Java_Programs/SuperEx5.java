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
class Animal1
{
    String color="white";
}
class Dog1 extends Animal1
{
    String color="black";
    void printColor1()
    {
        System.out.println(super.color);//prints color of Animal class
    }
}
class BabyDog1 extends Dog1
{
String color="brown";
    void printColor1()
    {
        System.out.println(color);
        System.out.println(super.color);
        super.printColor1();
    }
}
public class SuperEx5 {
    public static void main(String[] args) {
        BabyDog1 b=new BabyDog1();
        b.printColor1();
    }
}
