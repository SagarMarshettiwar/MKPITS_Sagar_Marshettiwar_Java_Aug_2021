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
class ComplexNumber { 
    private final double real; 
    private final double imaginary; 
    public ComplexNumber(double real, double imaginary) { 
        this.real = real; 
        this.imaginary = imaginary; 
    } 
    public ComplexNumber sum(int real1,int imaginary1) { 
        double r = this.real + real1; 
        double i = this.imaginary + imaginary1; 
        return new ComplexNumber(r, i); 
    } 
    public ComplexNumber difference(int real1,int imaginary1) { 
        double r = this.real - real1; 
        double i = this.imaginary - imaginary1; 
        return new ComplexNumber(r, i); 
    } 
    public double getReal() { 
        return real; 
    } 
    public double getImaginary() {
        return imaginary; 
    }  
    public String toString() { 
        return real + " + " + imaginary + "i"; 
    } 
}
public class TestEx9 {
    public static void main(String[] args) { 
        ComplexNumber c1 = new ComplexNumber(2, 4);
        ComplexNumber c2 = new ComplexNumber(3,5);
        System.out.println("first complex number: " + c1.toString()); 
        System.out.println("second complex number: " +c2.toString()); 
        System.out.println("sum of two complex numbers: " +c1.sum(3,5)); 
        System.out.println("difference of two complex numbers: " +c1.difference(3,5)); 
    } 
}