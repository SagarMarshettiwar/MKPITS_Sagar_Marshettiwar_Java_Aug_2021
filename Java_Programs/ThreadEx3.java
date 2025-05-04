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
class th3 extends Thread
{
    public void run()
    {
        for(int i=0;i<=3;i++)
        {
            System.out.println("A-"+getState());
        }
    }
}
class th4 extends Thread
{
    public void run()
    {
        for(int i=0;i<=3;i++)
        {
            System.out.println("B-"+getState());
        }
    }
}
public class ThreadEx3
{
    public static void main(String[] args) {
        th3 a=new th3();
        a.start();
        th4 b=new th4();
        b.start();
        Thread c=Thread.currentThread();
        System.out.println(c);
    }
}
