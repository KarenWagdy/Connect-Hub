/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author karen
 */
public class User {
    private int userId=1;
    private String email;
    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private String status;

    public User( String email, String username, String password, LocalDate dateOfBirth) {
        this.userId = getMaxId()+1;
        this.email = email;
        this.username = username;
        try {
            this.password = Functionalities.passwordHashing(password);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dateOfBirth = dateOfBirth;
        this.setStatus("offline");
    }
    public static int getMaxId()
    {
        ArrayList<User> user=UsersDatabase.usersArray;
        if(user.isEmpty())
        {
            return 0;
        }
        else 
        {
            return user.get(user.size()-1).getUserId();
        }
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public  int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getStatus() {
        return status;
    }
    
    
}
