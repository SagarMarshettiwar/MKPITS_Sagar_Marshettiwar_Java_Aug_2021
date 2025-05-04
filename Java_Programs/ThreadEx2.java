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
class T1 extends Thread
{
    public void run()
    {
        for(int i=0;i<=3;i++)
        {
            System.out.println("A-"+getPriority());
        }
    }
}
class T2 extends Thread
{
    public void run()
    {
        for(int i=0;i<=3;i++)
        {
            System.out.println("B-"+getPriority());
        }
    }
}
public class ThreadEx2
{
    public static void main(String[] args) {
        T1 a=new T1();
        a.start();
        T2 b=new T2();
        b.start();
        Thread c=Thread.currentThread();
        System.out.println(c);
    }
}
