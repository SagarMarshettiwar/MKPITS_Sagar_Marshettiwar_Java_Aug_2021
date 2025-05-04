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
public class Vehicles {
    int id;
    String name;
    public Vehicles() {
        id=1;
        name="sagar";
    }
    void display(){
        System.out.println(id+" "+name);
    }
    public static void main(String[] args) {
        Vehicles v=new Vehicles();
        v.display();
    }
}

