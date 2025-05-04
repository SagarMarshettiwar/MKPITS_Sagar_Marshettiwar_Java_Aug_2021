package com.example.sprbootcrufe.repository;

import com.example.sprbootcrufe.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepo extends JpaRepository<Login,Long> {

}
