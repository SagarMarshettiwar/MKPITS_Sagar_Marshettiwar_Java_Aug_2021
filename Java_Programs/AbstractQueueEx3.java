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
public class AbstractQueueEx3 {
    public static void main(String[] argv) throws Exception{
        AbstractQueue<Integer> AQ = new LinkedBlockingQueue<Integer>();
        AQ.add(10);
        AQ.add(20);
        AQ.add(30);
        AQ.add(40);
        AQ.add(50);
        System.out.println("AbstractQueue contains: " + AQ);
    }
}
