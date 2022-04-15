package com.example.springdemo123;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class Mycontroller {
    @RequestMapping
//    public String Display(){
//        return "page1";
//    }
   /* public String Display(Model mod){
        mod.addAttribute("msg","This is model type 2 object data");
        return "page1";
    }*/
//    public String Display(ModelMap mod){
//        mod.addAttribute("msg","This is model type 3 object data");
//        return "page1";
//    }
   /* public String Display(Map<String,Object> mod){
        mod.put("msg","This is model object type 4 data");
        return "page1";
    }*/
    public ModelAndView disp(){
        ModelAndView m=new ModelAndView("page1");
        m.addObject("msg","tjis is model and view ");
        return m;
    }
}
