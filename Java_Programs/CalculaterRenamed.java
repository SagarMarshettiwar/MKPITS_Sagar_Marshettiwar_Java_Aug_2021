/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StaticExample;

/**
 *
 * @author SAGAR
 */
public class Calculate {
    static int cube(int x){
        return x*x*x;
    }
    public static void main(String[] args) {
        int result=Calculate.cube(2);
        System.out.println(result);
    }
}
