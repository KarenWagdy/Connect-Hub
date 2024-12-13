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
public class FriendRequestNotification extends Notification {
    private int senderId;
    private int id=1;
    private int ReceiverId;
    public FriendRequestNotification(String message, String type, LocalDateTime time, int ReceiverId, int senderId) {
        super( message, type, time);
        this.senderId=senderId;
        this.ReceiverId=ReceiverId;
        this.id = getMaxId()+1;
    }

    public int getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(int ReceiverId) {
        this.ReceiverId = ReceiverId;
    }
    

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }
    
     public static int getMaxId() {
        ArrayList<Notification> notifications = FriendRequestNotificationDatabase.notificationsArray;
        if (notifications.isEmpty()) {
            return 0;
        } else {
            return notifications.get(notifications.size() - 1).getId();
        }
    }
    
    
    
}
