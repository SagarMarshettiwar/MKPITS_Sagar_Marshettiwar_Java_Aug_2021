package com.example.MKApi.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.MKApi.Models.Registration;

public interface RegestrationRepo extends MongoRepository<Registration, String> {
	Registration findByEmailAndPassword(String email, String password);
	
	Registration findByEmail(String email);
	
}