package com.example.MKApi.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MKApi.Dto.RegistrationDto;
import com.example.MKApi.Models.Registration;
import com.example.MKApi.Services.RegestrationService;

@RestController
@RequestMapping("/api")
public class RegistrationController {
	
	@Autowired
	private RegestrationService regestrationService;
	
	@PostMapping("/addData")
	public String adddata(@RequestBody RegistrationDto r) {                 
		Registration data = regestrationService.addData(r);
		return "Data Added";
	}
	
	@GetMapping("/getData")
	public List<Registration> adddata() {
		List<Registration> data = regestrationService.getData();
		return data;
	}
}
