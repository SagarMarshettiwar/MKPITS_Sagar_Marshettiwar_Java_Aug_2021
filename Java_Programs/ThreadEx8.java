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
class th11 extends Thread
{
    public void run()
    {    
        for(int i=0;i<=10;i++)
        {
                System.out.println("A-"+getName()+"= "+i);
        }
    }
}

public class ThreadEx8
{
    public static void main(String[] args) {
        th11 a1=new th11();
        th11 a2=new th11();
        th11 a3=new th11();
        th11 a4=new th11();
        th11 a5=new th11();
        th11 a6=new th11(); 
        
        a1.start();
//        try{
//            Thread.sleep(2000);
//        }catch(Exception e){
//            System.out.println(e);
//        }
        try{
            System.out.println("thread is suspended");
            a1.suspend();
            Thread.sleep(1000);
            System.out.println("thread is resume");
            a1.resume();
       }catch(Exception e){
            System.out.println(e);
        }
        a2.start();
        a3.start();
        a4.start();
        a5.start();
        a6.start();
        
    }
}
