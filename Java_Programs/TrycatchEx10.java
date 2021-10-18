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
public class TrycatchEx10 {
    public static void main(String[] args) {
        int arr[] =new int[5];
        int res=0;
        try {
            arr[6]=33;
        }catch (Exception ee) {
            System.out.println(ee.toString());
        }
        finally {
            System.out.println(res);
        }
            System.out.println("bye");
    }
}
