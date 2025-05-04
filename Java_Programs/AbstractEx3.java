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
abstract class AbstractEx3 {
    abstract void bike();
}
class Honda1 extends AbstractEx3{
    void bike(){
        System.out.println("running");
    }
    public static void main(String[] args) {
        AbstractEx3 a=new Honda1();
        a.bike();
    }
}