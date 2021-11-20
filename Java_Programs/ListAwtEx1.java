/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples5;
import java.awt.*;
/**
 *
 * @author SAGAR
 */
public class ListAwtEx1 extends Frame {
    ListAwtEx1(){
        setSize(400,400);
        setLayout(null);
        setVisible(true);
        
        List l=new List(5);
        l.setBounds(100,100,75,75);
        l.add("item 1");
        l.add("item 2");
        l.add("item 3");
        l.add("item 4");
        l.add("item 5");
        add(l);
    }
    public static void main(String[] args) {
        ListAwtEx1 l=new ListAwtEx1();
    }
}