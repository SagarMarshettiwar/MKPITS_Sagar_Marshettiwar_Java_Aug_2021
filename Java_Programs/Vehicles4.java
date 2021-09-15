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
public class Vehicles4 {
    int id;
    String name;
    int cost;
    
    public Vehicles4(int d,String n,int c) {
        id=d;
        name=n;
        cost=c;
    }
    public Vehicles4(){
    }
    void display(){
        System.out.println(id+" "+name+" "+cost);
    }
    public static void main(String[] args) {
        Vehicles4 v=new Vehicles4(1,"audi",123);
        Vehicles4 v1=new Vehicles4();
        v.display();
        v1.id=v.id;
        v1.name=v.name;
        v1.cost=v.cost;
        v1.display();
        
    }
}
