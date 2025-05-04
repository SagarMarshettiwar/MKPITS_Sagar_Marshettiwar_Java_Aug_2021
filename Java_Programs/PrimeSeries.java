/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrimeNumber;

/**
 *
 * @author SAGAR
 */
public class PrimeSeries {
    public static void main(String[] args) {
        int i=1,t=0;
        while(i<=25){
            int j=2;
            while(j<i){
                if(i%j==0){
                    t=t+1;
                }
                j++;
            }
            if(t==0){
            System.out.println(i);
            }else{
                t=0;
            }
            i++;
        }
    }
}
