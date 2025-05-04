/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThisKeywordExample;

/**
 *
 * @author SAGAR
 */
class B{
    B(){
        System.out.println("hello B");
    }
    B(int x){
        this();
        System.out.println(x);
    }
}
public class TestThis5 {
    public static void main(String[] args) {
    B b=new B(5);
    }
}
