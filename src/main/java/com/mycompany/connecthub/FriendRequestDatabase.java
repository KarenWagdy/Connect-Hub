/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    
    public static ArrayList<String> readFriendRequests(int userId) {
        ArrayList<String> friendRequests = new ArrayList<>();

        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("friendRequest.json")));
            JSONArray friendRequestJson = new JSONArray(jsonLines);

            for (int i = 0; i < friendRequestJson.length(); i++) {
                JSONObject request = friendRequestJson.getJSONObject(i);
                int senderId = request.getInt("senderId");
                int receiverId = request.getInt("receiverId");
                String status = request.getString("status");

                if (senderId == userId || receiverId == userId) {
                    friendRequests.add("Sender: " + senderId + ", Receiver: " + receiverId + ", Status: " + status);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return friendRequests;
    }

    public static void saveFriendRequest(int senderId, int receiverId) {
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("friendRequest.json")));
            JSONArray friendRequestJson = new JSONArray(jsonLines);

            JSONObject newFriendRequest = new JSONObject();
            newFriendRequest.put("senderId", senderId);
            newFriendRequest.put("receiverId", receiverId);
            newFriendRequest.put("status", "Pending");

            friendRequestJson.put(newFriendRequest);
            Files.write(Paths.get("friendRequest.json"), friendRequestJson.toString().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
