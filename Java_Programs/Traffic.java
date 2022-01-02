/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadsPack;

/**
 *
 * @author SAGAR
 */

class Red extends Thread{

    @Override
    public void run() {
        System.out.println("Red Light");
    }    
}
class Yellow extends Thread{

    @Override
    public void run(){
            System.out.println("Yellow Light"); 
    }
}
class Green extends Thread{

    @Override
    public void run() {
        System.out.println("Green Light");
    }
}
public class Traffic {
    public static void main(String[] args) {
        Red r=new Red();
        r.start();
        try{
            for(int i=15;i>=1;i--){
                Thread.sleep(1000);
                System.out.println(i);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        Green g=new Green();
        g.start();
        try{
            for(int j=10;j>=1;j--){
                Thread.sleep(1000);
                System.out.println(j);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        Yellow y=new Yellow();
        y.start();
        try{
                Thread.sleep(3000);
        }catch(Exception e){
            System.out.println(e);
        }
        Red r1=new Red();
        r1.start();
    }
}