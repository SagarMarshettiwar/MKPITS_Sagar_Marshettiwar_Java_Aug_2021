package com.example.tutorials;

public class UPPojo {
    String User;
    String Pass;

    public UPPojo(String user, String pass) {
        this.setUser(user);
        this.setPass(pass);
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
