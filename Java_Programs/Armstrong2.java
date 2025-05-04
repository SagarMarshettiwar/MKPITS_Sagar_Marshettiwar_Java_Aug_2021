/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MixedQuestions;

/**
 *
 * @author SAGAR
 */
public class Armstrong2 {
    public static void main(String[] args) {
        int i=1;
        while(i<500){
            int n=i;
            int sum=0;
            while(n>0){
                int r=n%10;
                sum=sum+(r*r*r);
                n=n/10;
            }
            if(sum==i){
                System.out.println("Armstrong"+i);
            }
         i++;   
        }
    }
} 
