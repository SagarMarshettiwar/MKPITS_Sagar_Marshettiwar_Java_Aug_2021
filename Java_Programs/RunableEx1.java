/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RunablePack;

/**
 *
 * @author SAGAR
 */

class thr1 implements Runnable{
    Thread t;
    public thr1() {
        t=new Thread(this);
        t.start();
    }
    
    @Override
    public void run() {
        System.out.println("hi ="+t.getName());
    }
}
public class RunableEx1 {
    public static void main(String[] args) {
        thr1 ob=new thr1();
        thr1 ob1=new thr1();
    }    
}