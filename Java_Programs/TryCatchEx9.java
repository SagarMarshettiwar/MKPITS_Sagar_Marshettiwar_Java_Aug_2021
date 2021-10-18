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
public class TryCatchEx9 {
    public static void main(String args[]){
        int n1=10;
        int n2=5;
        int res=0;
        try
        {
        res=n1/n2;
            System.out.println(res);
        }catch(Exception ee){
            System.out.println(ee.toString());
        }
        finally
        {
        System.out.println("finally res = " + res);
        }
    }
}
