/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InheritanceExamples;

/**
 *
 * @author SAGAR
 */
class Boss{
    public int salary=10000;
}
class Emp extends Boss{
    public int bonus=1000;
}
public class InheritanceEx2 {
    public static void main(String[] args) {
        Emp e=new Emp();
        System.out.println("salary ="+e.salary);
        System.out.println("bonus ="+e.bonus);
        System.out.println("total ="+(e.salary+e.bonus));
    }
}
