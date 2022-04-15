package com.example.sprbootcrude2.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter

@Entity
public class register {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    String uname;
    String upass;
    String city;
    String address;
    String phone;

}
