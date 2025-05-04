/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadPack2;

/**
 *
 * @author SAGAR
 */
class th21 extends Thread{
    public void run(){
        for(int i=0;i<=2;i++)
            System.out.println("Hi "+getName()+isDaemon());
    }
}
public class ThreadP2Ex1{
    public static void main(String[] args) {
        th21 a=new th21();
        th21 b=new th21();
        th21 c=new th21();

        a.start();
        b.setDaemon(true);
        b.start();
        c.start();
    }
}
