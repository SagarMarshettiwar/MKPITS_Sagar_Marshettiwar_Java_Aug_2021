/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Observable;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author SAGAR
 */
class Observer1 implements Observer{
    public void update(Observable obj, Object arg)
    {
        System.out.println("Observer1 is added");
    }
}
class BeingObserved extends Observable{
    void incre()
    {
        setChanged();
        notifyObservers();
    }
}
public class ObserverEx1 {
    public static void main(String args[]){
        BeingObserved beingObserved = new BeingObserved();
        Observer1 observer1 = new Observer1();
        beingObserved.addObserver(observer1);
        beingObserved.incre();
    }
}