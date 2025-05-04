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
public class StringBuffEx3 {
    public static void main(String[] args) {
        StringBuffer b=new StringBuffer("HelloSagar");
        b.replace(1, 5, "java");
        System.out.println(b);
    }
}