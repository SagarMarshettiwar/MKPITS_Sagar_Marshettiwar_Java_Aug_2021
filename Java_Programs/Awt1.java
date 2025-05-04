/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples3;
import java.awt.*;
/**
 *
 * @author SAGAR
 */
public class Awt1 extends Frame {
    Awt1(){
        setSize(400,400);
        setLayout(null);
        setVisible(true);
        
        Checkbox c1=new Checkbox("C++");
        c1.setBounds(50,100,50,50);
        add(c1);
        
        Checkbox c2=new Checkbox("java",true);
        c2.setBounds(100,100,50,50);
        add(c2);
    }
    public static void main(String[] args) {
        Awt1 a=new Awt1();
    }
}
