/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WeakHashMap;

import java.util.WeakHashMap;

/**
 *
 * @author SAGAR
 */
class Demo{
    public String toString()
    {
        return "demo";
    }
    public void finalize()
    {
        System.out.println("finalize method is called");
    }
}
public class WeakHashMapEx1 {
    public static void main(String args[])throws Exception{
        WeakHashMap m = new WeakHashMap();
        Demo d = new Demo();
        m.put(d," Hi ");
        System.out.println(m);
        d = null;
        System.gc();
    //try{        
        Thread.sleep(4000);
    //}catch(Exception e){
      //  System.out.println(e);
    //}        
        System.out.println(m);
    }
}
