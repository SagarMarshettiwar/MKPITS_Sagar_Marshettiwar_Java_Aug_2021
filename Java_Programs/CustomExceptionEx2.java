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
class NoFundException extends Exception{

    public NoFundException(String message) {
       //super(message);
        System.out.println(message);
    }
    
}
public class CustomExceptionEx2{
    public static void main(String[] args) throws NoFundException{
        //Thread.sleep(1000);
       int Balance=10000,Withdrawl=15000;
       if(Withdrawl < Balance){
           System.out.println("Collect Money:"+Withdrawl);
       }else{
           
           NoFundException a=new NoFundException("no fund");
           throw a;
       }
   }
}