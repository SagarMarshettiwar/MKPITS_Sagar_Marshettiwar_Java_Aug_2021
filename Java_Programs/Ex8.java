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
class TestSubject{
    public static void Calculate(){
        Scanner sc=new Scanner(System.in);
        int marks=0;
        for(int i=1;i<=3;i++){
            System.out.println("Enter 3 Subjects"+i);
            int m=sc.nextInt();
            marks=marks+m;
        }
        System.out.println("Total Marks ="+marks);
        Percentage(marks);  
    }
    public static void Percentage(int Tmark){
        float per=(Tmark/300.0f)*100.0f;
        System.out.println("Your Percentage"+per);
    }
}
public class Ex8 {
    public static void main(String[] args) {
        TestSubject.Calculate();
    }
}
