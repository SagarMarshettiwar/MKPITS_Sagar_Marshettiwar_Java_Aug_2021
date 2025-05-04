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
public class Palindrom6 {
    public static void main(String[] args) {
        int n,t,sum=0,r;
        n=454;
        t=n;
        while(n>0){
            r=n%10;
            sum=(sum*10)+r;
            n=n/10;
        }
        if(t==sum)
            System.out.println("palindrom");
        else
            System.out.println("not");
    }
}
