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
public class Vehicles3 {
    int id;
    String name;
    int cost;
    
    public Vehicles3(int d,String n,int c) {
        id=d;
        name=n;
        cost=c;
    }
    public Vehicles3(Vehicles3 s){
        id=s.id;
        name=s.name;
        cost=s.cost;
    }
    void display(){
        System.out.println(id+" "+name+" "+cost);
    }
    public static void main(String[] args) {
        Vehicles3 v=new Vehicles3(1,"audi",123);
        Vehicles3 v1=new Vehicles3(v);
        v.display();
        v1.display();
        
    }
}
