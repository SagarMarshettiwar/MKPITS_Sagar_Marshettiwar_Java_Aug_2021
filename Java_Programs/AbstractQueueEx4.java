/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractQueue;

import java.util.AbstractQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author SAGAR
 */
public class AbstractQueueEx4 {
    public static void main(String[] argv) throws Exception{
        AbstractQueue<Integer> AQ1 = new LinkedBlockingQueue<Integer>();
        AQ1.add(10);
        AQ1.add(20);
        AQ1.add(30);
        AQ1.add(40);
        AQ1.add(50);
        System.out.println("AbstractQueue contains : "+ AQ1);
        AbstractQueue<Integer> AQ2 = new LinkedBlockingQueue<Integer>();
        System.out.println("AbstractQueue2 initially contains : " + AQ2);
        AQ2.addAll(AQ1);
        System.out.println( "AbstractQueue1 after addition contains : " + AQ2);
    }
}
