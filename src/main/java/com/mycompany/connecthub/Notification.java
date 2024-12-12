/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.time.LocalDateTime;

/**
 *
 * @author X1
 */
public abstract class Notification {
    private int id=1;
    private String message;
    private String type;
    private LocalDateTime time; 
    

    public Notification(String message, String type, LocalDateTime time) {
        this.message = message;
        this.type = type;
        this.time = time;
        
    }

    public int getId() {
        return id;
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
