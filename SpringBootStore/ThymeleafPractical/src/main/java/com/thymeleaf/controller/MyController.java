package com.thymeleaf.controller;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyController {
	@RequestMapping(value ="/home",method = RequestMethod.GET)
	public String page(Model model) {
		model.addAttribute("name","Sagar Marshettiwar");
		model.addAttribute("date",new Date().toLocaleString());
		return "Home"; 	
	}
	
	@GetMapping("/itr")
	public String iterator(Model m) {
		List<String> listof = List.of("sagar","Sameer","sakshi","amit","paras");
		m.addAttribute("mylist",listof);
		return "itrloop";
	}
	
	@GetMapping("/condition")
	public String conditional(Model m) {
		m.addAttribute("isActive", true);
		Model attribute = m.addAttribute("gender","F");
		System.out.println(attribute);
		List<String> listof1 = List.of("2","1","7","6","9","11");
		m.addAttribute("mylist1",listof1);
		return "Condition";
	}
}
