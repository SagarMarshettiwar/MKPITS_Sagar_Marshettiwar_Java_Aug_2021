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
class th12 extends Thread
{
    public void run()
    {    
        for(int i=0;i<=10;i++)
        {
                System.out.println("A-"+getName()+"= "+i);
        }
    }
}

public class ThreadEx9
{
    public static void main(String[] args) {
        th12 a1=new th12();
        th12 a2=new th12();
//        th12 a3=new th12();
//        th12 a4=new th12();
//        th12 a5=new th12();
        th12 a6=new th12(); 
        
        a1.start();
        a2.start();
//        a3.start();
//        a4.start();
//        a5.start();
        a6.start();
        System.out.println(a2.getName()+"= "+a2.isAlive());
        try{
            a2.join();
            System.out.println(a2.getName()+"= "+a2.isAlive());
       }catch(Exception e){
            System.out.println(e);
        } 
    }
}

