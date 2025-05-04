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
public class PrimeSeries2 {
    public static void main(String[] args) {
        int t=0;
        for(int i=0;i<=25;i++){
            for(int j=2;j<i;j++){
                if(i%j==0){
                    t=t+1;
                }
            }if(t==0){
                System.out.println(i);
            }else{
                t=0;
            }
        }
    }
}
