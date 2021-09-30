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
interface Person{
    void name();
    interface Message{
        void msg();
    }
}
public class IntfEx13 implements Person.Message{
    public void name(){
        System.out.println("sagar");
    }
    public void msg(){
        System.out.println("msg");
    }
    public static void main(String[] args) {
        IntfEx13 s=new IntfEx13();
        s.name();
        s.msg();
    }
}
