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
class One{
   synchronized void disp(String s){
        System.out.print(" [ "+s);
        try{
            Thread.sleep(1000);
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.print(" ] ");
    }
}
class Thread1 implements Runnable{
    Thread t;
    String s;
    One o;
    public Thread1(One ob,String s1) {
        s=s1;
        o=ob;
        t=new Thread(this);
        t.start();
    }
    
    @Override
    public void run() {
        o.disp(s);
    }
}
public class RunableEx3 {
    public static void main(String[] args) {
        One o1=new One();
        Thread1 t1=new Thread1(o1,"sagar");
        Thread1 t2=new Thread1(o1,"marshettiwar");
        Thread1 t3=new Thread1(o1,"information Technology");
    }
}
