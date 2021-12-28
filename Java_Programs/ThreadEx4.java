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
class th5 extends Thread
{
    public void run()
    {
        for(int i=0;i<=3;i++)
        {
//            try{
//                Thread.sleep(10000);
//            }catch(Exception e){    
//                System.out.println(e);   
//            }    
                System.out.println("A-"+getThreadGroup());
        }
    }
}
class th6 extends Thread
{
    public void run()
    {
        for(int i=0;i<=3;i++)
        {
            System.out.println("B-"+getThreadGroup());
        }
    }
}
public class ThreadEx4
{
    public static void main(String[] args) {
        th5 a=new th5();
        a.start();
        th6 b=new th6();
        b.start();
        Thread c=Thread.currentThread();
        System.out.println(c);
    }
}
