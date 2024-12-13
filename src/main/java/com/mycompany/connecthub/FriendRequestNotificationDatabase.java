/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import static com.mycompany.connecthub.UsersDatabase.usersArray;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author X1
 */
public class FriendRequestNotificationDatabase {
    
    private static FriendRequestNotificationDatabase FRnotificationsDatabase=null;
    
    private FriendRequestNotificationDatabase()
    {
        
    }
    
    public static FriendRequestNotificationDatabase getInstance() {
        if (FRnotificationsDatabase == null) {
            FRnotificationsDatabase = new FriendRequestNotificationDatabase();
        }
        return FRnotificationsDatabase;  // if notification JSon File database is null make an object of it else return the same object
    }
    
    static ArrayList<FriendRequestNotification> notificationsArray = new ArrayList<>();
    
    public static ArrayList<FriendRequestNotification> readFriendReqNotifications() {
        notificationsArray.clear();

        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("FriendRequestnotifications.json")));
            JSONArray notifications = new JSONArray(jsonLines);

            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            for (int i = 0; i < notifications.length(); i++) {
                JSONObject notification = notifications.getJSONObject(i);
                int id  = notification.getInt("notificationId");
                String message = notification.getString("message");
                String type = notification.getString("type");
                LocalDateTime time = LocalDateTime.parse(notification.getString("time"), formatter);
                int recieverId = notification.getInt("RecieverId");
                int senderId=notification.getInt("senderId");

                notificationsArray.add(new FriendRequestNotification (message, type, time,recieverId , senderId));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return notificationsArray;
    }
    
    public static void saveFriendReqNotifications(ArrayList<FriendRequestNotification> notifications) {
        JSONArray notificationsArray = new JSONArray();
        for (FriendRequestNotification i : notifications) {
            JSONObject obj = new JSONObject();
            obj.put("notificationId", i.getId());
            obj.put("message", i.getMessage());
            obj.put("type", i.getType());
            obj.put("time", i.getTime());
            
            
          //if (i instanceof FriendRequestNotification) {
            //FriendRequestNotification friendRequest = (FriendRequestNotification) i;
            obj.put("senderId", i.getSenderId());
            obj.put("RecieverId",i.getReceiverId());
            
       // }
            notificationsArray.put(obj);

        }
        try {
            FileWriter file = new FileWriter("FriendRequestnotifications.json");
            file.write(notificationsArray.toString(4));
            file.close();
        } catch (IOException e) {

        }
    }
    
    public static ArrayList<FriendRequestNotification> readFriendReqNotificationsForUser(int userId)
    {
        ArrayList<FriendRequestNotification> notifications=new ArrayList<>();
        
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("FriendRequestnotifications.json")));
            JSONArray notificationJson = new JSONArray(jsonLines);
            
            for (int i = 0; i < notificationJson.length(); i++) {
                JSONObject notification = notificationJson.getJSONObject(i);
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                int id  = notification.getInt("notificationId");
                String message = notification.getString("message");
                String type = notification.getString("type");
                LocalDateTime time = LocalDateTime.parse(notification.getString("time"), formatter);
                int receiverId = notification.getInt("RecieverId");
                int senderId=notification.getInt("senderId");
                
                if ( receiverId == userId) {
                        FriendRequestNotification n=getFriendReqNotification(id);
                        notifications.add(n);
                } 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return notifications;
    }
    
    //method that takes notificationId and returns notification
    public static FriendRequestNotification getFriendReqNotification( int id)
    {    
         ArrayList<FriendRequestNotification> notifications = FriendRequestNotificationDatabase.readFriendReqNotifications();
        for (int i = 0; i < notifications.size(); i++) {
            if (notifications.get(i).getId() == id) {
                return notifications.get(i);
            }
        }
        return null;

    }
    
    public static FriendRequestNotification sendNotification(int recieverId)
    {
        FriendRequestNotification notification = new FriendRequestNotification(
                    Functionalities.currentUser.getUsername() + " sent you a friend request.",
                    "FriendRequest",
                    LocalDateTime.now(),
                    recieverId,  // Receiver's ID
                    Functionalities.currentUser.getUserId()  // Sender's ID
                );
        return notification;
    }
    
    
    
    
    
    

    
}
