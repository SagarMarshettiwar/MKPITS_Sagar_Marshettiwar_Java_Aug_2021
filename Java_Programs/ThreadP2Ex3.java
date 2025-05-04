/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ThreadPack2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author SAGAR
 */
class th23 extends Thread{
    public void run(){
        for(int i=0;i<=2;i++)
            if(isDaemon())
                System.out.println(getName()+"is daemon thread");
            else
                System.out.println(getName()+"is not daemon thread");
    }
}
public class ThreadP2Ex3{
    public static void main(String[] args) {
        th23 a=new th23();
        th23 b=new th23();
        th23 c=new th23();

        a.start();
        b.setDaemon(true);
        b.start();
        c.start();

        ExecutorService ex= Executors.newFixedThreadPool(2);
        ex.execute(a);
        ex.execute(b);
        ex.execute(c);
    }
}
