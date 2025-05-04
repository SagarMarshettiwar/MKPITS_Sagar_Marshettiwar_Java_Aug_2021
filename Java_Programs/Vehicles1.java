/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Examples;

/**
 *
 * @author SAGAR
 */
public class Vehicles1 {
    int id;
    String name;
    public Vehicles1(int d,String n) {
        id=d;
        name=n;
    }
    void display(){
        System.out.println(id+" "+name);
    }
    public static void main(String[] args) {
        Vehicles1 v=new Vehicles1(1,"audi");
        v.display();
    }
}
