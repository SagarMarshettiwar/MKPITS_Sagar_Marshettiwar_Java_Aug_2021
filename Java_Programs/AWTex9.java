/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AWTExamples2;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author SAGAR
 */
public class AWTex9 extends Frame implements ActionListener{
    Label l1,l2,l3,l4;
    Button b1;
    TextField t1,t2,t3;
    AWTex9(){
        setSize(400,400);
        setVisible(true);
        setLayout(null);
        
        l1=new Label("Empno =");
        l1.setBounds(20,50,80,20);
        add(l1);
        
        t1=new TextField();
        t1.setBounds(100,50,70,20);
        add(t1);
        
        l2=new Label("Emp name =");
        l2.setBounds(20,100,80,20);
        add(l2);
        
        t2=new TextField();
        t2.setBounds(100,100,70,20);
        add(t2);
        
        l3=new Label("Salary =");
        l3.setBounds(20,150,50,20);
        add(l3);
        
        t3=new TextField();
        t3.setBounds(100,150,70,20);
        add(t3);
        
        l4=new Label("Bonus =");
        l4.setBounds(20,200,200,20);
        add(l4);
        
        b1=new Button("Press");
        b1.setBounds(150,250, 60,20);
        add(b1);
        b1.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e) {
        int empno=Integer.parseInt(t1.getText());
        String name=t2.getName();
        double Salary=Double.parseDouble(t3.getText());
        double Bonus=0.0;
        if(e.getSource()==b1){
            if(Salary>=20000.0){
                Bonus=Salary+0.35;
            }else if(Salary>=10000.0){
                Bonus=Salary+0.25;
            }else{
                Bonus=Salary+0.15;
            }
        }
        l4.setText("Bonus ="+Bonus);
    }
    public static void main(String[] args) {
        AWTex9 a=new AWTex9();
    }
}
