package com.example.sprbootcrude2.repository;

import com.example.sprbootcrude2.model.login;
import com.example.sprbootcrude2.model.register;
import org.springframework.data.jpa.repository.JpaRepository;

public interface myregisterrepo extends JpaRepository<register,Integer> {
}
