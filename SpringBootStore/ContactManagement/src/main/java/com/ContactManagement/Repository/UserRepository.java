package com.ContactManagement.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ContactManagement.entities.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	
	public List<User> findByEmailAndPassword(String email,String password);
	@Query("select u from user u where u.email =: email")
	public User getUserbyusername(@Param("email") String email);
}
