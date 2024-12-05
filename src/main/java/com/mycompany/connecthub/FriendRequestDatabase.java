/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

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
 * @author nouri
 */
public class FriendRequestDatabase {

    private static FriendRequestDatabase frd = null;

    public FriendRequestDatabase() {
    }

    public static FriendRequestDatabase getInstance() {
        if (frd == null) {
            frd = new FriendRequestDatabase();
        }
        return frd;
    }

   
    public static void updateFriendRequest(int senderId, int receiverId) {
        try {

            String jsonLines = new String(Files.readAllBytes(Paths.get("friendRequest.json")));
            JSONArray friendRequests = new JSONArray(jsonLines);

            for (int i = 0; i < friendRequests.length(); i++) {
                JSONObject request = friendRequests.getJSONObject(i);
                if (request.getInt("senderId") == senderId && request.getInt("receiverId") == receiverId) {
                    friendRequests.remove(i);
                    break;
                }
            }

            Files.write(Paths.get("friendRequest.json"), friendRequests.toString().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> readFriendRequests(int userId) {
        ArrayList<User> friendRequests = new ArrayList<>();

        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("friendRequest.json")));
            JSONArray friendRequestJson = new JSONArray(jsonLines);

            for (int i = 0; i < friendRequestJson.length(); i++) {
                JSONObject request = friendRequestJson.getJSONObject(i);
                int senderId = request.getInt("senderId");
                int receiverId = request.getInt("receiverId");
                String status = request.getString("status");

                if ((senderId == userId || receiverId == userId) && "Pending".equalsIgnoreCase(status)) {
                    if (senderId != userId) {
                        User sender = Functionalities.getUser(senderId);
                        friendRequests.add(sender);
                    } else {
                        User receiver = Functionalities.getUser(receiverId);
                        friendRequests.add(receiver);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return friendRequests;
    }

    public static void saveFriendRequest(int senderId, int receiverId) {
        JSONArray friendRequestArray;
        try {
            File file = new File("friendRequest.json");
            if (file.exists()) {
                String jsonLines = new String(Files.readAllBytes(Paths.get("friendRequest.json")));
                friendRequestArray = new JSONArray(jsonLines);
            } else {
                friendRequestArray = new JSONArray();
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
            return;
        }

        JSONObject newFriendRequest = new JSONObject();
        newFriendRequest.put("senderId", senderId);
        newFriendRequest.put("receiverId", receiverId);
        newFriendRequest.put("status", "Pending");

        friendRequestArray.put(newFriendRequest);

        try {
            FileWriter fileWriter = new FileWriter("friendRequest.json");
            fileWriter.write(friendRequestArray.toString(2));
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    public static void acceptFriendRequest(int senderId, int receiverId) {
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("friendRequest.json")));
            JSONArray friendRequests = new JSONArray(jsonLines);

            boolean requestFound = false;

            for (int i = 0; i < friendRequests.length(); i++) {
                JSONObject request = friendRequests.getJSONObject(i);
                if (request.getInt("senderId") == senderId && request.getInt("receiverId") == receiverId) {
                    friendRequests.remove(i);
                    requestFound = true;
                    break;
                }
            }

            if (!requestFound) {
                System.out.println("Friend request not found!");
                return;
            }

            try (FileWriter fileWriter = new FileWriter("friendRequest.json")) {
                fileWriter.write(friendRequests.toString(2));
            }

            JSONObject newFriend = new JSONObject();
            newFriend.put("senderId", senderId);
            newFriend.put("receiverId", receiverId);

            File friendFile = new File("friend.json");
            JSONArray friends = new JSONArray();

            if (friendFile.exists()) {
                String friendJsonLines = new String(Files.readAllBytes(Paths.get("friend.json")));
                friends = new JSONArray(friendJsonLines);
            }

            friends.put(newFriend);

            try (FileWriter fileWriter = new FileWriter("friend.json")) {
                fileWriter.write(friends.toString(2));
            }

            System.out.println("Friend request accepted and added to friends list!");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error processing friend request.");
        }
    }

}
