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
class Address2
{
    String city;
    String state ;
    String country;
    Address2(String city,String state, String country) {
        this.city=city;
        this.state=state;
        this.country=country;
    }
}
class Customer1
{
    String custname;
    Address2 address;
    Customer1(String custname, Address2 address) {
        this.custname=custname;
        this.address=address;
    }
    void display() {
        System.out.println("custname is " + custname);
        System.out.println("city is " + address.city);
        System.out.println("state is " + address.state);
        System.out.println("country is " + address.country);
    }
}

public class AggregationEx4{
    public static void main(String[] arg) {
    Address2 adr1=new Address2("nagpur","maharashtra","india");
    Customer1 cust1=new Customer1("aditya",adr1);
    cust1.display();
    }
}
