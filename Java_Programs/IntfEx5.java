/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceExamples;

/**
 *
 * @author SAGAR
 */
interface shape2{
    void draw1();
}
class cir implements shape2{
    public void draw1(){
        System.out.println("circle coding");
    }
}
public class IntfEx5{
    public static void main(String[] arg)
    {
        cir c=new cir();
        c.draw1();
    }
}