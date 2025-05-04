package com.example.sprbootcrufe.controller;

import com.example.sprbootcrufe.model.Login;
import com.example.sprbootcrufe.repository.LoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Mycontroller {
    @Autowired
    private LoginRepo repo;

    @RequestMapping("/home")
    public String Display(){
        return "Page1";
    }

    @RequestMapping("/sucess")
    public String CollectData(@ModelAttribute("lob") Login ob){
        //repo.save(ob);
        //repo.deleteById(2l);
        repo.deleteAll();
        return "page2";
    }
}
