/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AggrigationExamples;

/**
 *
 * @author SAGAR
 */
class Address{
    public String city,State,country;

    public Address(String city, String State, String country) {
        this.city = city;
        this.State = State;
        this.country = country;
    }
}
class Emp{
    public int id;
    public String name;
    Address ad;

    public Emp(int id, String name, Address ad) {
        this.id = id;
        this.name = name;
        this.ad = ad;
    }
    void display(){
        System.out.println(id+" "+name);
        System.out.println(ad.city+" "+ad.State+" "+ad.country);
    }
}
public class AggrigationEx2 {
    public static void main(String[] args) {
        Address address1=new Address("gzb","UP","india");
        Address address2=new Address("gno","UP","ind");
        Emp e=new Emp(111,"varun",address1);
        Emp e2=new Emp(112,"arun",address2);
        e.display();
        e2.display();
    }
}
