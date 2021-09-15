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
public class Vehicles2 {
    int id;
    String name;
    int cost;
    public Vehicles2(int d) {
        id=d;
    }
    public Vehicles2(int d,String n) {
        id=d;
        name=n;
    }
    public Vehicles2(int d,String n,int c) {
        id=d;
        name=n;
        cost=c;
    }
    void display(){
        System.out.println(id+" "+name+" "+cost);
    }
    public static void main(String[] args) {
        Vehicles2 v=new Vehicles2(1);
        v.display();
        Vehicles2 v2=new Vehicles2(1,"audi");
        v2.display();
        Vehicles2 v3=new Vehicles2(1,"audi",123);
        v3.display();
    }
}