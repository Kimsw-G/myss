package com.security.myss.model;

import java.security.Timestamp;

import lombok.Data;

@Data
public class User {
    
    private int id;
    private String username;
    private String password;
    private String email;
    private String role;

    private String provider; // ex)google
    private String prioviderId;
    
    private Timestamp createDate;
}
