/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnumMap;

import java.util.EnumMap;

/**
 *
 * @author SAGAR
 */
public class EnumMapEx1 {
    public enum Code
    {
        CODE, CONTRIBUTE, QUIZ, MCQ;
    }
    public static void main(String args[]){
        EnumMap<Code, String> EMap = new EnumMap<Code, String>(Code.class);
        EMap.put(Code.CODE, "Start Coding with gfg");
        EMap.put(Code.CONTRIBUTE, "Contribute for others");
        EMap.put(Code.QUIZ, "Practice Quizes");
        EMap.put(Code.MCQ, "Test Speed with Mcqs");
        System.out.println("Size of EnumMap in java: "+EMap.size());
        System.out.println("EnumMap: " + EMap);
        System.out.println("Key : " + Code.CODE +" Value: "+ EMap.get(Code.CODE));
        System.out.println("Does EMap has "+Code.CONTRIBUTE+": "+ EMap.containsKey(Code.CONTRIBUTE));
        System.out.println("Does EMap has :" + Code.QUIZ + " : "+ EMap.containsValue("Practice Quizes"));
        System.out.println("Does EMap has :" + Code.QUIZ + " : "+ EMap.containsValue(null));
    }
}
