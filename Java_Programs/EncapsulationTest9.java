/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EncapsulationExample;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
class user1{
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String signIn(String un,String p){
        if(un.equals(userName)&&p.equals(password)){
            return "Welcome ="+userName;
        }else{
            return "invalid creadentials";
        }
    }
    public String signUp(String userName,String password){
        setUserName(userName);
        setPassword(password);
        return "user details saved successfully";
    }
}
public class EncapsulationTest9 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        user1 u=new user1();
        System.out.println("-------------------------------------------------");
        System.out.println("Please Register");
        System.out.println("Enter UserName");
        String USNM=s.next();
        System.out.println("Enter PassWord");
        String PASS=s.next();
        System.out.println("-------------------------------------------------");
        System.out.println(u.signUp(USNM, PASS));
        System.out.println("-------------------------------------------------");
        System.out.println("-------------------------------------------------");
        System.out.println("Now Login");
        System.out.println("Enter UserName");
        String USNM1=s.next();
        System.out.println("Enter PassWord");
        String PASS1=s.next();
        System.out.println("-------------------------------------------------");
        System.out.println(u.signIn(USNM1, PASS1));
        System.out.println("-------------------------------------------------");
    }
}
