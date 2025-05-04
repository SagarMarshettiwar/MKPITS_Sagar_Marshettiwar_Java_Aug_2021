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
class thr2 implements Runnable{
    Thread t;
    public thr2() {
        t=new Thread(this);
        t.start();
    }
    
    @Override
    public void run() {
        for(int i=1;i<=10;i++){
            if(i%2==0){
                System.out.println("even"+i);
            }else{
                System.out.println("odd"+i);
            }
        }
    }
}
public class RunableEx2 {
    public static void main(String[] args) {
        thr2 ob=new thr2();
    }    
}
