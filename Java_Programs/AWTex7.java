/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples2;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author SAGAR
 */
public class AWTex7 extends Frame implements ActionListener{
    Label l1,l2;
    TextField t1;
    Button b1;
    AWTex7(){
        setSize(300,300);
        setVisible(true);
        setLayout(null);
        
        l1=new Label("Enter number for Factorial");
        l1.setBounds(20,50,150,25);
        add(l1);
        
        t1=new TextField();
        t1.setBounds(180,50,100,25);
        add(t1);
        
        l2=new Label("Answer : ");
        l2.setBounds(20,150,150,25);
        add(l2);
        
        b1=new Button("Press");
        b1.setBounds(100,100,100,25);
        add(b1);
        b1.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int num=Integer.parseInt(t1.getText());
        int t=0;
        String result=null;
        if(e.getSource()==b1){
            for(int i=2;i<num;i++){
                if(num%i==0){
                    t=t+1;
                }
            }if(t==0){
                result="Prime";
            }else{
                result="Not Prime";
            }
        }
        l2.setText("Answer = "+result);
    }
    public static void main(String[] args) {
        AWTex7 a=new AWTex7();
    }
}
