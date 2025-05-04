package com.example.sprbootcrude2.repository;

import com.example.sprbootcrude2.model.login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface myloginrepo extends JpaRepository<login,Integer> {
    login findByuname(String uname);
}