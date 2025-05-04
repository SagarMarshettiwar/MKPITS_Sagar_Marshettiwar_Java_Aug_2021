package com.ContactManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ContactManagement.Repository.UserRepository;
import com.ContactManagement.entities.User;
import com.ContactManagement.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class UserController {
	@Autowired
	private UserRepository urepo;
	@Autowired
	private NormalController normalController;
	@RequestMapping("/")	
	public String home(Model model) {
		model.addAttribute("title","Home - Contact Management");
		return "home";
	}
	
	@RequestMapping("/about")	
	public String about(Model model) {
		model.addAttribute("title","About - Contact Management");
		return "about";
	}
	
	@RequestMapping("/signup")	
	public String signup(Model model) {
		model.addAttribute("title","Signup - Contact Management");
		model.addAttribute("user",new User());
		return "signup";
	}
	
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("title","Login - Contact Management");
		return "login";
	}
	
	@RequestMapping(value="/pass",method=RequestMethod.POST)	
	public String Loginuser(@ModelAttribute("userlogin") User user,Model model,HttpSession session) {
		try {
			List<User> byEmailAndPassword = urepo.findByEmailAndPassword(user.getEmail(),user.getPassword());
			System.out.println(byEmailAndPassword);
			if(byEmailAndPassword.isEmpty()) {
				session.setAttribute("umessage",new Message("Login Failed","alert-danger"));
				return "login";
			}else if(byEmailAndPassword.get(0).getRole().equals("ROLE_ADMIN")){
				normalController.UserDashboard(byEmailAndPassword.get(0).getEmail());
				model.addAttribute("userlogin",new User());
				session.setAttribute("umessage",new Message("Successfully ADMIN Login","alert-success"));
				return "redirect:/user/index";
			}else {
				model.addAttribute("userlogin",new User());
				session.setAttribute("umessage",new Message("Successfully  Login","alert-success"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("userlogin",user);
			session.setAttribute("umessage",new Message("Something Went wrong !!"+e.getMessage(),"alert-danger"));
		}
		return "home";
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)	
	public String register(@Valid @ModelAttribute("user") User user,BindingResult result,@RequestParam(value="aggriment",defaultValue = "false") boolean aggriment,Model model,HttpSession session) {
		try {
			if(result.hasErrors()) {
				model.addAttribute("user",user);
				return "signup";
			}
			if(!aggriment) {
				throw new Exception("Terms and condition not checked");
			}
			user.setRole("ROLE_USER");
			user.setEnable(true);
			User save = urepo.save(user);
			System.out.println(save);
			model.addAttribute("user",new User());
			session.setAttribute("message",new Message("Successfully Registered","alert-success"));
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message",new Message("Something Went wrong !!"+e.getMessage(),"alert-danger"));
		}
		return "signup";
	}
}
