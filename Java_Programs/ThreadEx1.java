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
public class ThreadEx1 extends Thread
{
    public void run()
    {
        for(int i=0;i<=3;i++)
        {
            System.out.println(Thread.currentThread());
            System.out.println("A-"+getName()+isAlive());
        }
    }
}
class th2 extends Thread
{
    public void run()
    {
        for(int i=0;i<=3;i++)
        {
            System.out.println("B-"+getName()+isAlive());
        }
    }
}
class example1
{
    public static void main(String[] args) {
        ThreadEx1 a=new ThreadEx1();
        a.start();
        th2 b=new th2();
        b.start();
        Thread c=Thread.currentThread();
        System.out.println(c.getName());
        
    }
}
