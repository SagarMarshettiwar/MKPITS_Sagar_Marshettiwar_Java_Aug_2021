package com.example.MKApi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.MKApi.Dto.RegistrationDto;
import com.example.MKApi.Models.Registration;
import com.example.MKApi.Services.LoginService;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api")
public class LoginController {
	@Autowired
	private LoginService loginService ;
	
	@PostMapping("/validateUser")
	public Registration findrecords(@RequestBody RegistrationDto r) {
		Registration user = loginService.findUser(r);
		return user;
	}
	
	@PostMapping("/forgetpassword/sendEmail")
	public ResponseEntity<String> sendEmail(@RequestBody RegistrationDto r,HttpSession session) {
	    ResponseEntity<String> sendEmail = loginService.sendEmail(r,session);
		return sendEmail;
	}
	
	@GetMapping("/forgetpassword/verifyotp")
    public ResponseEntity<String> verifyOtp(@RequestParam String otp,HttpSession session) {
		ResponseEntity<String> verifyOtp = loginService.verifyOtp(otp,session);
        return verifyOtp;
    }
	
	@PostMapping("/forgetpassword/changePassword")
	public ResponseEntity<String> changepassword(@RequestBody RegistrationDto r,HttpSession session) {
	  ResponseEntity<String> changePassword = loginService.changePassword(r,session);
		return changePassword;
	}
	
}
