/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OverloadingExamples;

/**
 *
 * @author SAGAR
 */
class student {
    void getdata(String name) {
        System.out.println("name is " + name);
    }   
    void getdata(String name,String address) {
        this.getdata("amit");
        System.out.println("address is " + address);
    }
}
public class OLex8 {
    public static void main(String[] args) {
        student s1=new student();
        s1.getdata("sagar","sadar");
    }
}