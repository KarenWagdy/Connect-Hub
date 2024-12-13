/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import static com.mycompany.connecthub.GroupActivitiesNotification.getMaxId;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public class AddPostNotification extends Notification{
    
    private int id=1;
    private int groupId;
    private int receiverIds;
    
    public AddPostNotification(String message, String type, LocalDateTime time,int groupId,int receiverIds) {
        super(message, type, time);
        this.groupId = groupId;
        this.receiverIds=receiverIds;
        this.id = getMaxId()+1;
    }
    
     public static int getMaxId() {
        ArrayList<Notification> notifications = GroupActivitiesNotificationDatabase.GRnotificationsArray;
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

    public int getReceiverIds() {
        return receiverIds;
    }
    
    
}
