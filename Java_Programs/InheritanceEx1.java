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
class Parents{
    public String firstName;
    public String lastName;
}
class Children extends Parents{
    public String address;
    public String job;
}
public class InheritanceEx1 {
    public static void main(String[] args) {
        Children c=new Children();
        c.firstName="Sagar";
        c.lastName="Marshettiwar";
        c.address="abc";
        c.job="java developer";
        System.out.println("Fn ="+c.firstName);
        System.out.println("Ln ="+c.lastName);
        System.out.println("A ="+c.address);
        System.out.println("J ="+c.job);
    }
}
