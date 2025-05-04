package com.example.springdemomvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@RequestMapping("/control")
public class MyController {
    @RequestMapping("/home")
    public String display(){
        return "Page1";
    }

    /*@RequestMapping("/sucess")
    //public String collectdata(@RequestParam("lname") String name , @RequestParam("lpass") String pass , Model mod){
    public String collectdata(@RequestParam(defaultValue = "defaultname",name="lname") String name , @RequestParam("lpass") String pass , Model mod){
        mod.addAttribute("name1",name);
        mod.addAttribute("pass1",pass);
        return "Page2";
    }*/

    @RequestMapping("/sucess")
    public String collectdata(@RequestParam("lname") String name , @RequestParam("lpass") String pass , Model mod){
        Login ob=new Login();
        ob.setLname(name);
        ob.setLpass(pass);

        mod.addAttribute("lob",ob);
        return "Page2";
    }

//    @RequestMapping("/home2")
//    public String Display1(){
//        return "Page2";
//    }
}
