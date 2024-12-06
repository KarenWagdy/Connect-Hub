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
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public static ArrayList<User> readFriendsFile() {
        FriendsArray.clear();

        try {

            String jsonLines = new String(Files.readAllBytes(Paths.get("Friends.json")));
            JSONArray Friends = new JSONArray(jsonLines);

            for (int i = 0; i < Friends.length(); i++) {
                JSONObject user = Friends.getJSONObject(i);
                int user1Id = user.getInt("user1Id");
                int user2Id = user.getInt("user2Id");

                User user2 = Functionalities.getUser(user2Id);
                FriendsArray.add(user2);

                User user1 = Functionalities.getUser(user1Id);
                FriendsArray.add(user1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return FriendsArray;
    }

    public static ArrayList<User> readFriends(int userId) {
        FriendsArray.clear();

        try {

            String jsonLines = new String(Files.readAllBytes(Paths.get("Friends.json")));
            JSONArray Friends = new JSONArray(jsonLines);

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
        JSONArray Friends = new JSONArray();
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("Friends.json")));
            Friends = new JSONArray(jsonLines);
        } catch (IOException e) {
        }

        JSONObject obj = new JSONObject();
        obj.put("user1Id", Functionalities.currentUser.getUserId());
        obj.put("user2Id", userId);

        Friends.put(obj);

        try {
            FileWriter file = new FileWriter("Friends.json");
            file.write(Friends.toString(2));
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public static void removeFriendFromFile(int userId) {
    try {
        String jsonLines = new String(Files.readAllBytes(Paths.get("Friends.json")));
        JSONArray friends = new JSONArray(jsonLines);

        JSONArray updatedFriends = new JSONArray();

        for (int i = 0; i < friends.length(); i++) {
            JSONObject friend = friends.getJSONObject(i);
            int user1Id = friend.getInt("user1Id");
            int user2Id = friend.getInt("user2Id");

            if (!((user1Id == Functionalities.currentUser.getUserId() && user2Id == userId) ||
                  (user2Id == Functionalities.currentUser.getUserId() && user1Id == userId))) {
                updatedFriends.put(friend);
            }
        }

        Files.write(Paths.get("Friends.json"), updatedFriends.toString(2).getBytes()); // Pretty-print with indentation

    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error while removing friend.");
    }
}

    public static boolean isFriend(int otherUserId) {
    try {
        String jsonLines = new String(Files.readAllBytes(Paths.get("Friends.json")));
        JSONArray friends = new JSONArray(jsonLines);

        for (int i = 0; i < friends.length(); i++) {
            JSONObject friend = friends.getJSONObject(i);
            int user1Id = friend.getInt("user1Id");
            int user2Id = friend.getInt("user2Id");

            if ((user1Id == Functionalities.currentUser.getUserId() && user2Id == otherUserId) ||
                (user2Id == Functionalities.currentUser.getUserId() && user1Id == otherUserId)) {
                return true; 
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error while checking friendship.");
    }
    return false; 
}
}
