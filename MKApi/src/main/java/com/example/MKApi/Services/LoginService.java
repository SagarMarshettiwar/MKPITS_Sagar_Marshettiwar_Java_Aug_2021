package com.example.MKApi.Services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.MKApi.Dto.RegistrationDto;
import com.example.MKApi.Models.Registration;
import com.example.MKApi.Repository.RegestrationRepo;

import jakarta.servlet.http.HttpSession;

@Service
public class LoginService {
	@Autowired
	RegestrationRepo repo;
	@Autowired
	EmailService emailService;
	
	public ResponseEntity<String> findUser(RegistrationDto r) {
		boolean matches=false;
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Registration registration=new Registration();
		registration.setEmail(r.getEmail());
		Registration byEmail = repo.findByEmail(registration.getEmail());
		if(byEmail != null) {
			matches = passwordEncoder.matches(r.getPassword(), byEmail.getPassword());	
		}else {
			return ResponseEntity.badRequest().body("Unable to find User");
		}
		
		if(!matches) {
			return ResponseEntity.badRequest().body("Your Email and Password must be wrong . Login UNSUCCESSFULL2");
		}else {
			return ResponseEntity.ok("Login SUCCESSFULL");
		} 
	}
	
    public ResponseEntity<String> sendEmail(@RequestBody RegistrationDto r, HttpSession session ) {
        Registration registration = new Registration();
        registration.setEmail(r.getEmail());
   
        Registration existingRegistration = repo.findByEmail(registration.getEmail());
        if (existingRegistration == null) {
            return ResponseEntity.badRequest().body("Email is not present in records");
        }
        
        Random random = new Random();
        int otp = random.nextInt(999999); 
        String otpString = String.format("%06d", otp);
        String emailTo=registration.getEmail();
        String Subject="Your OTP for Forget Password";
        boolean sendEmail = emailService.sendEmail(emailTo,Subject,otpString);
        if(sendEmail) {
        	session.setAttribute("otp", otpString);
            session.setAttribute("email", emailTo);
            return ResponseEntity.ok("OTP has been sent to " + registration.getEmail());
        }
       
        return ResponseEntity.ok("Problem in SENDEmail class" + sendEmail);
    }

	public ResponseEntity<String> verifyOtp(String otp, HttpSession session) {
		String sessionOtp = (String) session.getAttribute("otp");
        if (otp.equals(sessionOtp)) {
        	 System.out.println("valid otp");
        	 session.removeAttribute("otp");
            return ResponseEntity.ok("OTP verified successfully ");
        } else if(sessionOtp == null){
        	return ResponseEntity.ok("OTP Already Verifiied ");
        }else {
            System.out.println("Invalid otp");
            return ResponseEntity.badRequest().body("Invalid OTP. Please try again.");
        }
	}

	public ResponseEntity<String> changePassword(RegistrationDto r, HttpSession session) {
		 Registration registration = new Registration();
	        registration.setEmail(r.getEmail());
	        String sessionEmail = (String) session.getAttribute("email");
	        Registration existingRegistration = repo.findByEmail(sessionEmail);
	        
	        if (existingRegistration == null) {
	            return ResponseEntity.badRequest().body("Email is not present in records");
	        }
	        
	        if(r.getPassword().equals(existingRegistration.getPassword())) {
	        	return ResponseEntity.badRequest().body("Your Change Password should not be match");
	        }else {
	        	existingRegistration.setPassword(r.getPassword());
		        repo.save(existingRegistration);
		        return ResponseEntity.ok("Password Change successfully");
	        }
	        
	}

}
