/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import static com.mycompany.connecthub.GroupActivitiesNotificationDatabase.GRnotificationsArray;
import static com.mycompany.connecthub.GroupActivitiesNotificationDatabase.getGroupNotification;
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
public class AddPostNotificationDatabase {
    private static AddPostNotificationDatabase AddPnotificationsDatabase=null;
    
    private AddPostNotificationDatabase()
    {
        
    }
    
    public static AddPostNotificationDatabase getInstance() {
        if (AddPnotificationsDatabase == null) {
            AddPnotificationsDatabase = new AddPostNotificationDatabase();
        }
        return AddPnotificationsDatabase;  // if notification JSon File database is null make an object of it else return the same object
    }
    
     static ArrayList<Notification> addPostsnotificationsArray = new ArrayList<>();
    
    public static ArrayList<Notification> readGroupPostsNotifications() {
        addPostsnotificationsArray.clear();

        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("AddPostsnotifications.json")));
            JSONArray notifications = new JSONArray(jsonLines);

            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            for (int i = 0; i < notifications.length(); i++) {
                JSONObject notification = notifications.getJSONObject(i);
                int id  = notification.getInt("notificationId");
                String message = notification.getString("message");
                String type = notification.getString("type");
                LocalDateTime time = LocalDateTime.parse(notification.getString("time"), formatter);
                // Retrieve receiverIds as JSONArray
            JSONArray receiverIdsJson = notification.getJSONArray("receiverIds");

            // Convert JSONArray to ArrayList<Integer>
            ArrayList<Integer> receiverIds = new ArrayList<>();
            for (int j = 0; j < receiverIdsJson.length(); j++) {
                receiverIds.add(receiverIdsJson.getInt(j));
            }
                
                int groupId=notification.getInt("GroupId");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return addPostsnotificationsArray;
    }
    
    public static void saveGroupPostsNotifications(ArrayList<Notification> notifications) {
        JSONArray notificationsArray = new JSONArray();
        for (Notification i : notifications) {
            JSONObject obj = new JSONObject();
            obj.put("notificationId", i.getId());
            obj.put("message", i.getMessage());
            obj.put("type", i.getType());
            obj.put("time", i.getTime());
          //if (i instanceof GroupActivitiesNotification) {
            AddPostNotification addPostNotification = (AddPostNotification) i;
            obj.put("GroupId", addPostNotification.getGroupId());
            obj.put("receicersId",new JSONArray(addPostNotification.getReceiverIds()));
       // }
            notificationsArray.put(obj);

        }
        try {
            FileWriter file = new FileWriter("AddPostsnotifications.json");
            file.write(notificationsArray.toString(4));
            file.close();
        } catch (IOException e) {

        }
    }
    public static ArrayList<Notification> readPostNotificationsForUser(int userId)
    {
        ArrayList<Notification> notifications=new ArrayList<>();
         ArrayList<Integer> receiverIds = new ArrayList<>();
          
        
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("AddPostsnotifications.json")));
            JSONArray notificationJson = new JSONArray(jsonLines);
            
            for (int i = 0; i < notificationJson.length(); i++) {
                
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
               JSONObject notification = notificationJson.getJSONObject(i);
                 int id= notification.getInt("notificationId");
                String message = notification.getString("message");
                String type = notification.getString("type");
                LocalDateTime time = LocalDateTime.parse(notification.getString("time"), formatter);
                    // Retrieve receiverIds as JSONArray
            JSONArray receiverIdsJson = notification.getJSONArray("receiverIds");

            // Convert JSONArray to ArrayList<Integer>
           
            for (int j = 0; j < receiverIdsJson.length(); j++) {
                receiverIds.add(receiverIdsJson.getInt(j));
            }
                
                int groupId=notification.getInt("GroupId");
                for(int z=0;z<receiverIds.size();z++){
                if ( receiverIds.get(z) == userId) {
                        Notification n=getPostNotification(id);
                        notifications.add(n);
                } 
            }

            }
               }
         catch (IOException e) {
            e.printStackTrace();
        }

        return notifications;
    }
     public static Notification getPostNotification( int id)
    {    
         ArrayList<Notification> notifications = AddPostNotificationDatabase.readGroupPostsNotifications();
        for (int i = 0; i < notifications.size(); i++) {
            if (notifications.get(i).getId() == id) {
                return notifications.get(i);
            }
        }
        return null;

    }
     
      public static AddPostNotification sendPostNotification(int recieverIds,int groupId)
    {
        Group group=GroupDatabase.getGroup(groupId);
        
        AddPostNotification notification = new AddPostNotification(
                    Functionalities.currentUser.getUsername() + "Posted on "+group.getName()+ "group",
                    "AddPost",
                    LocalDateTime.now(),
                    groupId,
                    recieverIds 
                );
        return notification;
    }
}



