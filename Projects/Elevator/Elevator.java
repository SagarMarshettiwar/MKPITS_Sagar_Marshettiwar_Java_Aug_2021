/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MKChallenge;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author SAGAR
 */
public class Elevator{
    JFrame f;
    JToggleButton b1,b2,b3,b4,b5,b6,bl,bf;
    JButton bo,bc;
    JPanel p,p1,p2,p3;
    JLabel l1,l2,l3,l4;
    public int currentfloor=0;
    public int destinationfloor=0;
    
    Elevator(){
        f=new JFrame();
        f.setSize(400,500);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        p1=new JPanel();
        p1.setBounds(50,100,100,100);
        p1.setBorder((Border) new RoundBtn(15));
        f.add(p1);
        
        l4=new JLabel("0");
        l4.setFont(new Font("Serif", Font.PLAIN, 45));
        p1.add(l4);
        
        p2=new JPanel();
        p2.setBounds(50,210,100,100);
        p2.setBorder((Border) new RoundBtn(15));
        f.add(p2);
        l4=new JLabel();
        l4.setBounds(0,0,100,80);
        p2.add(l4);
        
        p3=new JPanel();
        p3.setBounds(50,330,100,100);
        p3.setBorder((Border) new RoundBtn(15));
        f.add(p3);
        
        l1=new JLabel("FAN :");
        p3.add(l1);
        
        l2=new JLabel("LiGHT :");
        p3.add(l2);
        
        l3=new JLabel("DOOR :");
        p3.add(l3);
        
        b1=new JToggleButton("1");
        b1.setBounds(200,100,50,50);
        b1.setBorder((Border) new RoundBtn(15));
        f.add(b1);
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(b1.isSelected()){
                    moveLift(1);
                }
            }
        });
        
        b2=new JToggleButton("2");
        b2.setBounds(280,100,50,50);
        b2.setBorder((Border) new RoundBtn(15));
        f.add(b2);
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(b2.isSelected()){
                    moveLift(2);
                }
            }
        });
        
        b3=new JToggleButton("3");
        b3.setBounds(200,170,50,50);
        b3.setBorder((Border) new RoundBtn(15));
        f.add(b3);
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(b3.isSelected()){
                    moveLift(3);
                }
            }
        });
        
        b4=new JToggleButton("4");
        b4.setBounds(280,170,50,50);
        b4.setBorder((Border) new RoundBtn(15));
        f.add(b4);
        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(b4.isSelected()){
                    moveLift(4);
                }
            }
        });
        
        b5=new JToggleButton("5");
        b5.setBounds(200,240,50,50);
        b5.setBorder((Border) new RoundBtn(15));
        f.add(b5);
        b5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(b5.isSelected()){
                    moveLift(5);
                }
            }
        });
        
        b6=new JToggleButton("6");
        b6.setBounds(280,240,50,50);
        b6.setBorder((Border) new RoundBtn(15));
        f.add(b6);
        b6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(b6.isSelected()){
                    moveLift(6);
                }
            }
        });
        
        bl=new JToggleButton("L");//Light
        bl.setBounds(200,310,50,50);
        bl.setBorder((Border) new RoundBtn(15));
        f.add(bl);
        bl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(bl.isSelected()){
                    l1.setText("LIGHT: ON");
                }else{
                    l1.setText("LIGHT: OFF");
                }
            }
        });
        
        bf=new JToggleButton("F");//Fan
        bf.setBounds(280,310,50,50);
        bf.setBorder((Border) new RoundBtn(15));
        f.add(bf);
        bf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(bf.isSelected()){
                    l2.setText("FAN: ON");
                }else{
                    l2.setText("FAN: OFF");
                }
            }
        });
        
        bo=new JButton("O");//Open door
        bo.setBounds(200,380,50,50);
        bo.setBorder((Border) new RoundBtn(15));
        f.add(bo);
        bo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==bo){
                    l3.setText("DOOR : OPEN");
                }
            }
        });
        
        bc=new JButton("C");//close Door
        bc.setBounds(280,380,50,50);
        bc.setBorder((Border) new RoundBtn(15));
        f.add(bc);
        bc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==bc){
                    l3.setText("DOOR : CIOSE");
        }
            }
        });
    }
    
    public void delay(int ms){
           try{
               Thread.sleep(ms);
           }catch(Exception e){
               System.out.println(e);
           }
       } 
       void moveLift(int d){
           destinationfloor=d;
                if(currentfloor < destinationfloor){
                    scaleImageup();
                    while(currentfloor++ < destinationfloor){//for(int i=currentfloor;i<destinationfloor;++i){
                        delay(600);
                        System.out.println(+currentfloor);
                        //l4.setText(String.valueOf(currentfloor));
                    }
                }
                else if(currentfloor > destinationfloor){//for(int i=currentfloor;i>destinationfloor;--i)
                    scaleImagedown();
                    while(currentfloor-- > destinationfloor){
                        delay(600);
                        System.out.println(+currentfloor);
                    }
               }     
       }
       public void scaleImageup(){
        ImageIcon uparrow=new ImageIcon("E:/img1.png");
        Image img=uparrow.getImage();
        Image scale=img.getScaledInstance(l4.getWidth(), l4.getHeight(), Image.SCALE_DEFAULT);
        ImageIcon scaledIcon=new ImageIcon(scale);
        l4.setIcon(scaledIcon);
   }    
   public void scaleImagedown(){
        ImageIcon downarrow=new ImageIcon("E:/img2.png");
        Image img1=downarrow.getImage();
        Image scale1=img1.getScaledInstance(l4.getWidth(), l4.getHeight(), Image.SCALE_DEFAULT);
        ImageIcon scaledIcon1=new ImageIcon(scale1);
        l4.setIcon(scaledIcon1);
   }    
    
    public static void main(String[] args) {
        Elevator e=new Elevator();
    }
}
class RoundBtn implements Border {
    private int r;
    RoundBtn(int r) {
        this.r = r;
    }
    public Insets getBorderInsets(Component c) {
        return new Insets(this.r+1, this.r+1, this.r+2, this.r);
    }
    public boolean isBorderOpaque() {
        return true;
    }
    public void paintBorder(Component c, Graphics g, int x, int y,int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, r, r);
    }
}