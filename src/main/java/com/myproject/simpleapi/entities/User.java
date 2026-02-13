package com.myproject.simpleapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private long id;
    
    @Column(name = "name", nullable = false)
    private String firstName;
    
    @Column(name = "email", nullable = false)
    private String email;
}
