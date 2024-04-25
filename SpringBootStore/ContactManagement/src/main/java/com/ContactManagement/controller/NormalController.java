package com.ContactManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class NormalController {
	@RequestMapping("/index")
	public String UserDashboard() {
		return "user/user_dashbord";
	}
}
