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
class th10 extends Thread
{
    public void run()
    {    
        for(int i=0;i<=10;i++)
        {
                System.out.println("A-"+getName());
        }
    }
}

public class ThreadEx7
{
    public static void main(String[] args) {
        th10 a1=new th10();
        th10 a2=new th10();
        th10 a3=new th10();
        th10 a4=new th10();
        th10 a5=new th10();
        th10 a6=new th10(); 
        a1.setName("sagar");
        a2.setName("saurabh");
        a3.setName("Yash");
        a4.setName("priyanka");
        a5.setName("vaidhai");
        a6.setName("rasika");
        a1.start();
        a2.start();
        a3.start();
        a4.start();
        a5.start();
        a6.start();
        
    }
}
