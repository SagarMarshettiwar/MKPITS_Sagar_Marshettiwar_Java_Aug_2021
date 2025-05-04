/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mkpits;

/**
 *
 * @author SAGAR
 */
class Even extends Exception{

    public Even(String message1) {
        System.out.println(message1);
    }
    
}
class Odd extends Exception{

    public Odd(String message) {
       //super(message);
        System.out.println(message);
    }
}
public class CustomException{
    public static void main(String[] args) throws Even,Odd{
        //Thread.sleep(1000);
      // int n=10;
       int n=5;
       if(n%2==0){
//           System.out.println("even");
           Even e=new Even("even number");
           throw e;
           
       }else{
//           System.out.println("Odd");
           Odd o=new Odd(" odd number");
           throw o;
       }
   }
}
