/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import static com.mycompany.connecthub.FriendRequestNotificationDatabase.getFriendReqNotification;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author X1
 */

public class GroupActivitiesNotificationDatabase {
    
    
        private static GroupActivitiesNotificationDatabase GRnotificationsDatabase=null;
    
    private GroupActivitiesNotificationDatabase()
    {
        
    }
    
    public static GroupActivitiesNotificationDatabase getInstance() {
        if (GRnotificationsDatabase == null) {
            GRnotificationsDatabase = new GroupActivitiesNotificationDatabase();
        }
        return GRnotificationsDatabase;  // if notification JSon File database is null make an object of it else return the same object
    }
    
     static ArrayList<GroupActivitiesNotification> GRnotificationsArray = new ArrayList<>();
    
    public static ArrayList<GroupActivitiesNotification> readGroupNotifications() {
        GRnotificationsArray.clear();

        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("GroupActivitiesnotifications.json")));
            JSONArray notifications = new JSONArray(jsonLines);

            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            for (int i = 0; i < notifications.length(); i++) {
                JSONObject notification = notifications.getJSONObject(i);
                int id  = notification.getInt("notificationId");
                String message = notification.getString("message");
                String type = notification.getString("type");
                LocalDateTime time = LocalDateTime.parse(notification.getString("time"), formatter);
                int recieverId = notification.getInt("RecieverId");
                int groupId=notification.getInt("GroupId");

                GRnotificationsArray.add(new GroupActivitiesNotification (id,message, type, time,recieverId , groupId));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return GRnotificationsArray;
    }
    
    
    
    public static void saveGroupNotifications(ArrayList<GroupActivitiesNotification> notifications) {
        JSONArray notificationsArray = new JSONArray();
        for (GroupActivitiesNotification i : notifications) {
            JSONObject obj = new JSONObject();
            obj.put("notificationId", i.getId());
            obj.put("message", i.getMessage());
            obj.put("type", i.getType());
            obj.put("time", i.getTime());
            
            
          //if (i instanceof GroupActivitiesNotification) {
            //GroupActivitiesNotification GRnotification = (GroupActivitiesNotification) i;
            obj.put("GroupId", i.getGroupId());
            obj.put("RecieverId",i.getReceiverId());

            
       // }
            notificationsArray.put(obj);

        }
        try {
            FileWriter file = new FileWriter("GroupActivitiesnotifications.json");
            file.write(notificationsArray.toString(4));
            file.close();
        } catch (IOException e) {

        }
    }
    
    public static ArrayList<GroupActivitiesNotification> readGroupNotificationsForUser(int userId)
    {
        ArrayList<GroupActivitiesNotification> notifications=new ArrayList<>();
        
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("GroupActivitiesnotifications.json")));
            JSONArray notificationJson = new JSONArray(jsonLines);
            
            for (int i = 0; i < notificationJson.length(); i++) {
                
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
               JSONObject notification = notificationJson.getJSONObject(i);
                int id  = notification.getInt("notificationId");
                String message = notification.getString("message");
                String type = notification.getString("type");
                LocalDateTime time = LocalDateTime.parse(notification.getString("time"), formatter);
                int receiverId = notification.getInt("RecieverId");
                int groupId=notification.getInt("GroupId");
                if ( receiverId == userId) {
                        GroupActivitiesNotification n=getGroupNotification(id);
                        notifications.add(n);
                } 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return notifications;
    }
    
     public static GroupActivitiesNotification getGroupNotification( int id)
    {    
         ArrayList<GroupActivitiesNotification> notifications = GroupActivitiesNotificationDatabase.readGroupNotifications();
        for (int i = 0; i < notifications.size(); i++) {
            if (notifications.get(i).getId() == id) {
                return notifications.get(i);
            }
        }
        return null;

    }
    
    public static GroupActivitiesNotification sendGroupNotification(int recieverId,int groupId)
    {
        Group group=GroupDatabase.getGroup(groupId);
        
        GroupActivitiesNotification notification = new GroupActivitiesNotification(
                    Functionalities.currentUser.getUsername() + "Added you to "+group.getName()+ "group",
                    "GroupActivity",
                    LocalDateTime.now(),
                    recieverId,  // Receiver's ID
                    groupId // Sender's ID
                );
        return notification;
    }
    
    
}


