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
    
     static ArrayList<AddPostNotification> addPostsnotificationsArray = new ArrayList<>();
    
    public static ArrayList<AddPostNotification> readGroupPostsNotifications() {
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
            JSONArray receiverIdsJson = notification.getJSONArray("receiversId");

            // Convert JSONArray to ArrayList<Integer>
            ArrayList<Integer> receiverIds = new ArrayList<>();
            for (int j = 0; j < receiverIdsJson.length(); j++) {
                receiverIds.add(receiverIdsJson.getInt(j));
            }
                
                int groupId=notification.getInt("GroupId");

             AddPostNotification newNotification = new AddPostNotification( message, type, time, groupId, receiverIds);
            addPostsnotificationsArray.add(newNotification);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return addPostsnotificationsArray;
    }
    
    public static void saveGroupPostsNotifications(ArrayList<AddPostNotification> notifications) {
    JSONArray notificationsArray = new JSONArray();
    for (AddPostNotification i : notifications) {
        JSONObject obj = new JSONObject();
        obj.put("notificationId", i.getId());
        obj.put("message", i.getMessage());
        obj.put("type", i.getType());
        obj.put("time", i.getTime());

        // Cast Notification to AddPostNotification
        //AddPostNotification addPostNotification = (AddPostNotification) i;
        obj.put("GroupId", i.getGroupId());

        // Get group and initialize receiver IDs list
        Group group = GroupDatabase.getGroup(i.getGroupId());
        ArrayList<Integer> receiversId = new ArrayList<>();

        // Add primary admin ID
        User primaryAdmin = Functionalities.getUser(group.getPrimaryAdmin());
        if (primaryAdmin != null) {
            if(primaryAdmin.getUserId()!=Functionalities.currentUser.getUserId())
            receiversId.add(primaryAdmin.getUserId());
        }

        // Add group member IDs
        for (User member : group.getMembers()) {
            if (!receiversId.contains(member.getUserId())) {  // Avoid duplicates
                if(member.getUserId()!=Functionalities.currentUser.getUserId())
                { receiversId.add(member.getUserId());}
            }
        }

        // Add group admin IDs
        for (User admin : group.getAdmins()) {
            if (!receiversId.contains(admin.getUserId())) {  // Avoid duplicates
               if(admin.getUserId()!=Functionalities.currentUser.getUserId())
                receiversId.add(admin.getUserId());
            }
        }

        // Add receiversId to JSON object
        obj.put("receiversId", new JSONArray(receiversId));

        // Add object to notifications array
        notificationsArray.put(obj);
    }

    // Write JSON array to file
    try (FileWriter file = new FileWriter("AddPostsnotifications.json")) {
        file.write(notificationsArray.toString(4)); // Pretty print with indentation
    } catch (IOException e) {
        e.printStackTrace();
    }
}

        
        
        
        
        
        
        
        
        
        
        
    
   /*     JSONArray notificationsArray = new JSONArray();
        for (Notification i : notifications) {
            JSONObject obj = new JSONObject();
            obj.put("notificationId", i.getId());
            obj.put("message", i.getMessage());
            obj.put("type", i.getType());
            obj.put("time", i.getTime());
          //if (i instanceof GroupActivitiesNotification) {
            AddPostNotification addPostNotification = (AddPostNotification) i;
            obj.put("GroupId", addPostNotification.getGroupId());
            Group group=GroupDatabase.getGroup(addPostNotification.getGroupId());
            //obj.put("receiverId",addPostNotification.getReceiverId());
            ArrayList<Integer> receiversId=new ArrayList<>();
            for(int z=0;z<group.getMembers().size();z++)
            { 
                receiversId.add(group.getMembers().get(z).getUserId());
                    
            }
            
            obj.put("receiversId",new JSONArray(receiversId));
       // }
            notificationsArray.put(obj);

        }
        try {
            FileWriter file = new FileWriter("AddPostsnotifications.json");
            file.write(notificationsArray.toString(4));
            file.close();
        } catch (IOException e) {

        }
    }*/
    
    
    public static ArrayList<AddPostNotification> readPostNotificationsForUser(int userId)
    {
        ArrayList<AddPostNotification> notifications=new ArrayList<>();
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
            JSONArray receiverIdsJson = notification.getJSONArray("receiversId");

            // Convert JSONArray to ArrayList<Integer>
           receiverIds.clear();
            for (int j = 0; j < receiverIdsJson.length(); j++) {
                receiverIds.add(receiverIdsJson.getInt(j));
            }
            int groupId=notification.getInt("GroupId");
            for (int z = 0; z < receiverIds.size(); z++) {
                if (receiverIds.get(z) == userId) {
                    AddPostNotification n = getPostNotification(id);
                    if (n != null) {
                        notifications.add(n);
                    } else {
                        System.err.println("Warning: Notification with ID " + id + " is null.");
                    }
                }
            }
                
                
               /* for(int z=0;z<receiverIds.size();z++){
                if ( receiverIds.get(z) == userId) {
                        Notification n=getPostNotification(id);
                        notifications.add(n);
                } 
            }*/

            }
               }
         catch (IOException e) {
            e.printStackTrace();
        }

        return notifications;
    }
     public static AddPostNotification getPostNotification( int id)
    {    
         ArrayList<AddPostNotification> notifications = AddPostNotificationDatabase.readGroupPostsNotifications();
        for (int i = 0; i < notifications.size(); i++) {
            if (notifications.get(i).getId() == id) {
                return notifications.get(i);
            }
        }
        return null;

    }
     
      public static AddPostNotification sendPostNotification(ArrayList<Integer> recieverIds,int groupId)
    {
        Group group=GroupDatabase.getGroup(groupId);
        
        AddPostNotification notification = new AddPostNotification(
                    Functionalities.currentUser.getUsername() + " Posted on "+group.getName()+ " group",
                    "AddPost",
                    LocalDateTime.now(),
                    groupId,
                    recieverIds 
                );
        return notification;
    }
}



