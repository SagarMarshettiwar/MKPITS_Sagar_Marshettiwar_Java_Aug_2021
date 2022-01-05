/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networkpack;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author SAGAR
 */
public class NetworkEx1 {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress ob=InetAddress.getLocalHost();
        System.out.println(ob);
        System.out.println(ob.getHostAddress());
        System.out.println(ob.getHostName());
        System.out.println(ob.getByName("www.google.com"));
        System.out.println(ob.getLoopbackAddress());
    }
}
