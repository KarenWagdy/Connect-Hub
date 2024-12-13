/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public abstract class Notification {
    //private int id;
    private String message;
    private String type;
    private LocalDateTime time; 
    

    public Notification(String message, String type, LocalDateTime time) {
        this.message = message;
        this.type = type;
        this.time = time;
        
    }

    
    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getTime() {
        return time;
    }

}
