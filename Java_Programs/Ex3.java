/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionPackages;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
public class Ex3 {
     public long isPerfect(long n){
            long sum=0;
            for(int i=1;i<=n/2;i++){
                if(n%i==0){
                    sum=sum+i;
                }
            }
            return sum;
        }
}
class TestPerfect{
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the number");
        long num=sc.nextInt();
        //long s=Ex3.isPerfect(num);
        Ex3 e=new Ex3();
        long s=e.isPerfect(num);
        if(s==num){
            System.out.println("perfect number ="+num);
        }else{
            System.out.println("not perfect number");
        }
    }
}