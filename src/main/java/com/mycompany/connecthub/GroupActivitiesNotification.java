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
//adding member and changing their status
public class GroupActivitiesNotification extends Notification{
    private int groupId;
    private int id=1;
    private int ReceiverId;
    public GroupActivitiesNotification(String message, String type, LocalDateTime time, int ReceiverId,int groupId) {
        super(message, type, time);
        this.groupId = groupId;
        this.ReceiverId=ReceiverId;
        this.id = getMaxId()+1;
    }
     public GroupActivitiesNotification(int id,String message, String type, LocalDateTime time, int ReceiverId,int groupId)
     {super(message, type, time);
        this.groupId = groupId;
        this.ReceiverId=ReceiverId;
        this.id =id;
         
     }    

    public int getId() {
        return id;
    }
    
    public static int getMaxId() {
        ArrayList<GroupActivitiesNotification> notifications = GroupActivitiesNotificationDatabase.GRnotificationsArray;
        if (notifications.isEmpty()) {
            return 0;
        } else {
            return notifications.get(notifications.size() - 1).getId();
        }
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(int ReceiverId) {
        this.ReceiverId = ReceiverId;
    }
    
    
    
    
    
    
}
