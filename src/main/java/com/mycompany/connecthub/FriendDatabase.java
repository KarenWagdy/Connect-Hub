/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

/**
 *
 * @author X1
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author X1
 */
public class FriendDatabase {

    static ArrayList<User> FriendsArray = new ArrayList<>();

    private static FriendDatabase FD = null;

    private FriendDatabase() {

    }

    public static FriendDatabase getInstance()//create only one object of the class
    {

        if (FD == null) { //if FD is null create a new object
            FD = new FriendDatabase();

        }
        //if FD not equal to null return object
        return FD;
    }

    public static ArrayList<User> readFriends(int userId) {
        FriendsArray.clear();

        try {

            String jsonLines = new String(Files.readAllBytes(Paths.get("Friends.json")));
            JSONArray Friends = new JSONArray(jsonLines);

            //DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
            // DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            for (int i = 0; i < Friends.length(); i++) {
                JSONObject user = Friends.getJSONObject(i);
                int user1Id = user.getInt("user1Id");
                int user2Id = user.getInt("user2Id");
                if (user1Id == userId) {
                    User user2 = Functionalities.getUser(user2Id);
                    FriendsArray.add(user2);
                }
                if (user2Id == userId) {
                    User user1 = Functionalities.getUser(user1Id);
                    FriendsArray.add(user1);

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return FriendsArray;
    }

    
    public static void saveFriends(int userId) {
            JSONArray FriendsSaveArray = new JSONArray();
        
            JSONObject obj = new JSONObject();
            obj.put("user1Id", Functionalities.currentUser.getUserId());
            obj.put("user2Id", userId);
           
            FriendsSaveArray.put(obj);
            
        try {
            FileWriter file = new FileWriter("Friends.json");
            file.write(FriendsSaveArray.toString(2));
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
