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
class th22 extends Thread{
    public void run(){
        for(int i=0;i<=2;i++)
            if(isDaemon())
                System.out.println(getName()+"is daemon thread");
            else
                System.out.println(getName()+"is not daemon thread");
    }
}
public class ThreadP2Ex2{
    public static void main(String[] args) {
        th22 a=new th22();
        th22 b=new th22();
        th22 c=new th22();

        a.start();
        b.setDaemon(true);
        b.start();
        c.start();
    }
}
