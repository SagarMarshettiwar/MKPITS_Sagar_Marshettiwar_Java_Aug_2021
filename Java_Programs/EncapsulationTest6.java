/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EncapsulationExample;

/**
 *
 * @author SAGAR
 */
class Calculate{
    private float roi;

    public void setRoi(float roi){
        this.roi=roi;
    }
    public float calinterest(float pa,int nom)
    {
        float pi=pa * roi * nom;
        return pi;  
    }
}

public class EncapsulationTest6 {
    public static void main(String[] args) {
        Calculate t=new Calculate();
        t.setRoi(20);
        float ta=t.calinterest(1000,12);
        System.out.println("total amount with interest " + ta);
    }
}

