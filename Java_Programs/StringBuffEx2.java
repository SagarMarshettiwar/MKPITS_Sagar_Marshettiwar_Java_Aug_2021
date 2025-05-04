/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StringBuffer;

/**
 *
 * @author SAGAR
 */
public class StringBuffEx2 {
    public static void main(String[] args) {
        StringBuffer b=new StringBuffer("HelloSagar");
        b.insert(1, "HI");
        System.out.println(b);
        b.replace(1, 5, "java");
        System.out.println(b);
    }
}
