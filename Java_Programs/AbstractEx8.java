/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractionExamples;

/**
 *
 * @author SAGAR
 */
abstract class P{
    abstract void op();
}
abstract class Q {
    abstract void OL();
}
class W extends P{
    void op(){
        System.out.println("hi");
    }
}        
class E extends Q{
    void OL(){
        System.out.println("hehee");
    }
}        
public class AbstractEx8 {
    public static void main(String[] args) {
        P p=new W();
        p.op();
        Q q=new E();
        q.OL();
    }
}
//Rule: If you are extending an abstract class 
//      that has an abstract method,
//      you must either provide the implementation 
//      of the method or make this class abstract.