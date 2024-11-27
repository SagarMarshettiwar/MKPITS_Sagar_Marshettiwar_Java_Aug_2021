package com.example.MKApi.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.MKApi.Dto.RegistrationDto;
import com.example.MKApi.Models.Registration;
import com.example.MKApi.Repository.RegestrationRepo;

@Service
public class RegestrationService {
	@Autowired
	RegestrationRepo repo;
	
	public Registration addData(RegistrationDto r){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Registration registration=new Registration();
		registration.setFirstName(r.getFirstName());
		registration.setLastName(r.getLastName());
		registration.setEmail(r.getEmail());
		registration.setPhoneNumber(r.getPhoneNumber());
		registration.setCity(r.getCity());
		registration.setState(r.getState());
		registration.setZip(r.getZip());
		registration.setCountry(r.getCountry());
		registration.setRole(r.getRole());
		String encode = passwordEncoder.encode(r.getPassword());
		registration.setPassword(encode);
		registration.setApproved(r.isApproved());
		registration.setAgentid(r.getAgentid());
		return repo.save(registration);
	}
	
	public List<Registration> getData() {
		return repo.findAll();
	}
}
