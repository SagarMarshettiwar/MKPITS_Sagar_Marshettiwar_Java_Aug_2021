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
class th9 extends Thread
{
    public void run()
    {    
        for(int i=0;i<=10;i++)
        {
            if(getName().equals("Thread-5")){
                System.out.println("hi");
            }
                System.out.println("A-"+getName());
        }
    }
}

public class ThreadEx6
{
    public static void main(String[] args) {
        th9 a1=new th9();
        th9 a2=new th9();
        th9 a3=new th9();
        th9 a4=new th9();
        a1.start();
        a2.start();
        a3.start();
        a4.start();
        th9 a5=new th9();
        a5.start();
        th9 a6=new th9(); 
        a6.start();
    }
}
