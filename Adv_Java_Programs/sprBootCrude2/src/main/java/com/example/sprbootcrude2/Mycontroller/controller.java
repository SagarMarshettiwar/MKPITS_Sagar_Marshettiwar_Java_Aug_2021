package com.example.sprbootcrude2.Mycontroller;

import com.example.sprbootcrude2.model.login;
import com.example.sprbootcrude2.model.register;
import com.example.sprbootcrude2.repository.myloginrepo;
import com.example.sprbootcrude2.repository.myregisterrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class controller {
    @Autowired
    private myloginrepo repo;
    @Autowired
    private myregisterrepo repo1;

    @RequestMapping(value = "/register")
    public String Display(){
        return "Regester";
    }
    @RequestMapping("/Success")
    public String CollectData(@ModelAttribute register ob){
        login l=new login();
        l.setUname(ob.getUname());
        l.setUpass(ob.getUpass());
        repo1.save(ob);
        repo.save(l);
        return "login";
    }
    @RequestMapping("/Success1" )
    public String CollectData1(@ModelAttribute login ob1) {
        login byname=repo.findByuname(ob1.getUname());
        if(byname != null){

            if(byname.getUname().equals(ob1.getUname())){

                if(byname.getUpass().equals(ob1.getUpass())){
                    return "Dashbord";
                }
            }
        }
        return "redirect: /register";
    }
}
