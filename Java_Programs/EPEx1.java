/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExceptionPropogation;

/**
 *
 * @author SAGAR
 */
class cal{
    int divide(int n1,int n2){
        int res=n1/n2;
        return res;
    }
}
class cal2 extends cal{
    int caldiv(int n,int m){
        int res=0;
        try{
            res=divide(n,m);
            return res;
        }catch(ArithmeticException e){
            System.out.println(e);
        }
        return res;
    }
}
public class EPEx1 {
    public static void main(String[] args) {
        cal2 c=new cal2();
        int r=c.caldiv(2, 0);
        System.out.println(r);
        System.out.println("reached end");
    }
}