/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperKeyWordExamples;

/**
 *
 * @author SAGAR
 */
class Square{
    public int Sq(int r){
        return r*r;
    }
}
class Area extends Square{
    public double pi=3.14;
    public double Ar(){
        return pi*super.Sq(10);
    }
}
public class SuperEx3 {
    public static void main(String[] args) {
        Area a=new Area();
        System.out.println("Area ="+a.Ar());
    }
}