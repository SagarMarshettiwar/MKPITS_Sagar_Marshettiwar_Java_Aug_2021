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
class User{
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
    public String checkLogin(String un,String p){
        if(un.equals("sagar")&&p.equals("sagar")){
            return "Welcome "+un;
        }else{
            return "invalid credentials";
        }
    }
}
public class EncapsulationTest8 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        User u=new User();
        System.out.println("Enter user name");
        String USNM=sc.next();
        System.out.println("Enter password");
        String PASS=sc.next();
        u.setUserName(USNM);
        u.setPassword(PASS);
        System.out.println(u.checkLogin(u.getUserName(),u.getPassword()));
    }
}
