/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AggrigationExamples;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
class Address1{
    public String city,State,country;

    public Address1(String city, String State, String country) {
        this.city = city;
        this.State = State;
        this.country = country;
    }
}
class Emp1{
    public int id;
    public String name;
    Address1 ad;

    public Emp1(int id, String name, Address1 ad) {
        this.id = id;
        this.name = name;
        this.ad = ad;
    }
    void display1(){
        System.out.println(id+" "+name);
        System.out.println(ad.city+" "+ad.State+" "+ad.country);
    }
}
public class AggrigationEx3 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        System.out.println("enter city");
        String city=s.next();
        System.out.println("enter state");
        String state=s.next();
        System.out.println("enter country");
        String country=s.next();
        System.out.println("enter emp id");
        int id=s.nextInt();
        System.out.println("enter name");
        String name=s.next();
        Address1 ada=new Address1(city,state,country);
        Emp1 e=new Emp1(id,name,ada);
        e.display1();
    }
}
