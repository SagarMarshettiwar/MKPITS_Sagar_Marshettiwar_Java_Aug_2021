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
class th7 extends Thread
{
    public void run()
    {
        for(int i=0;i<=3;i++)
        {
            System.out.println("A-"+getUncaughtExceptionHandler());
        }
    }
}
class th8 extends Thread
{
    public void run()
    {
        for(int i=0;i<=3;i++)
        {
            System.out.println("B-"+getUncaughtExceptionHandler());
        }
    }
}
public class ThreadEx5
{
    public static void main(String[] args) {
        th7 a=new th7();
        a.start();
        th8 b=new th8();
        b.start();
        Thread c=Thread.currentThread();
        System.out.println(c);
    }
}