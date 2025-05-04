/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractQueuList;

import java.util.AbstractSequentialList;
import java.util.LinkedList;

/**
 *
 * @author SAGAR
 */
public class AbstractQueuListEx1 {
    public static void main(String[] args){
        AbstractSequentialList<Integer> absl= new LinkedList<>();
        absl.add(5);
        absl.add(6);
        absl.add(7);
        System.out.println(absl);
    }
}
