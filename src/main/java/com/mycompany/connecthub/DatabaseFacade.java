/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class DatabaseFacade {

    public DatabaseFacade() {

    }

   

    public static ArrayList<Group> readGroups() {
         return GroupDatabase.getInstance().readGroups();
    }
    
     public static ArrayList<User> readUsers() {
         return UsersDatabase.getInstance().readUsers();
    }
     
      public static ArrayList<User> readFriends() {
         return FriendDatabase.getInstance().readFriendsFile();
    }
      
       public static ArrayList<FriendRequestNotification> readFriendReqNotifications() {
         return FriendRequestNotificationDatabase.getInstance().readFriendReqNotifications();
    }
        public static ArrayList<GroupActivitiesNotification> readGroupActivitiesNotifications() {
         return GroupActivitiesNotificationDatabase.getInstance().readGroupNotifications();
    }
        
     
     
    

}
