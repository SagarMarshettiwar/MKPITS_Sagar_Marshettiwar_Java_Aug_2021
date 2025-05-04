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
public class AbstractQueueEx2 {
    public static void main(String[] argv) throws Exception{
        AbstractQueue<Integer> AQ1 = new LinkedBlockingQueue<Integer>();
        AQ1.add(10);
        AQ1.add(20);
        AQ1.add(30);
        AQ1.add(40);
        AQ1.add(50);
        System.out.println("AbstractQueue1 contains : " + AQ1);
         int head = AQ1.remove();
        System.out.println("head : " + head);
        System.out.println("AbstractQueue1 after removal of head : " + AQ1);
        AQ1.clear();
        System.out.println("AbstractQueue1 : " + AQ1);
    }
}