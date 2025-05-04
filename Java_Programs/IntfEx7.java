/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceExamples;

/**
 *
 * @author SAGAR
 */
interface Bank{
    float rateOfInterest();
}
class SBI implements Bank{
    public float rateOfInterest(){
        return 9.15f;
    }
}
class PNB implements Bank{
    public float rateOfInterest(){
        return 9.7f;
    }
}
public class IntfEx7{
    public static void main(String[] args){
        Bank b;
        b=new SBI();
        System.out.println("ROI: "+b.rateOfInterest());
        b=new PNB();
        System.out.println("ROI: "+b.rateOfInterest());
    }
}