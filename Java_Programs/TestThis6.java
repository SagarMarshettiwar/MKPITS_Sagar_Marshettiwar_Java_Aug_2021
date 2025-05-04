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
class C{
    C(){
        this(5);
        System.out.println("hello B");
    }
    C(int x){
        System.out.println(x);
    }
}
public class TestThis6 {
    public static void main(String[] args) {
    C c=new C();
    }
}
