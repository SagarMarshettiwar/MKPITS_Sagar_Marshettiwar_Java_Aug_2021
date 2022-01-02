/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mkpits;

/**
 *
 * @author SAGAR
 */
interface D{
    int a=10;
    void cake();
//    void sum(){  //interface dont have method with body.
//        
//    }    
 }
    
interface B{
    void car();
}
interface C{
    void dog();
}
class A{
    int b;
    void food(){
        System.out.println("hello");
    }    
}
public class InterfaceExEx extends A implements B,C,D{
    public static void main(String[] args) {
        InterfaceExEx x=new InterfaceExEx();
        System.out.println(x.a);
    }

    @Override
    public void car() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dog() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cake() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
