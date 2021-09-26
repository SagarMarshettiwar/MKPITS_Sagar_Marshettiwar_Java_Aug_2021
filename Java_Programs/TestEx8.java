/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestExamples;

/**
 *
 * @author SAGAR
 */
class Average{
    int n1,n2,n3;

    public Average(int n1, int n2, int n3) {
        this.n1 = n1;
        this.n2 = n2;
        this.n3 = n3;
        System.out.println("Average ="+(n1+n2+n3)/3);
    }
}
public class TestEx8 {
    public static void main(String[] args) {
        Average a=new Average(4,5,6);
    }
}