/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TryCatch;

/**
 *
 * @author SAGAR
 */
public class TryCatchEx5 {
    public static void main(String[] args) {
        int i=50,j=0,data;
        try{
            data=i/j;
            System.out.println(data);
        }catch(Exception e){
            System.out.println(i/(j+2));
        }
    }
}