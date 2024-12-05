/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author nouri
 */
public class FriendRequestManagement {

    public static void manageFriendRequest(int senderId, int receiverId, String newStatus) {
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("friendRequest.json")));
            JSONArray friendRequestJson = new JSONArray(jsonLines);

            for (int i = 0; i < friendRequestJson.length(); i++) {
                JSONObject request = friendRequestJson.getJSONObject(i);

                if (request.getInt("senderId") == senderId && request.getInt("receiverId") == receiverId) {
                    request.put("status", newStatus);
                    break;
                }
            }

            Files.write(Paths.get("friendRequest.json"), friendRequestJson.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> suggestFriends(
            int userId,
            ArrayList<Integer> allUserIds,
            ArrayList<Integer> friendIds,
            ArrayList<JSONObject> requests
    ) {
        // Create a copy of allUserIds to avoid modifying the original list
        ArrayList<Integer> suggestions = new ArrayList<>(allUserIds);

        // Remove the user's own ID
        suggestions.remove(Integer.valueOf(userId));

        // Remove current friends
        suggestions.removeAll(friendIds);

        // Remove users involved in pending requests
        for (JSONObject request : requests) {
            int senderId = request.getInt("senderId");
            int receiverId = request.getInt("receiverId");

            // If the user has sent or received a request, exclude the other party
            if (senderId == userId) {
                suggestions.remove(Integer.valueOf(receiverId));
            } else if (receiverId == userId) {
                suggestions.remove(Integer.valueOf(senderId));
            }
        }

        return suggestions;
    }

    public static ArrayList<User> search(String username) {
        ArrayList<User> users = UsersDatabase.readUsers();
        ArrayList<User> result = new ArrayList<>();
        for (User user : users) {
            if (user.getUsername().contains(username)) {
                result.add(user);
            }
        }
        return result;
    }

    public static void blockUser(int userId, int blockedUserId) {
        try {
            String json = new String(Files.readAllBytes(Paths.get("blockedUsers.json")));
            JSONArray blockedUsers = new JSONArray(json);

            JSONObject newBlock = new JSONObject();
            newBlock.put("userId", userId);
            newBlock.put("blockedUserId", blockedUserId);
            blockedUsers.put(newBlock);

            Files.write(Paths.get("blockedUsers.json"), blockedUsers.toString().getBytes());
            System.out.println("User blocked!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeFriend(int userId, int friendId) {
        try {
            String json = new String(Files.readAllBytes(Paths.get("friends.json")));
            JSONArray friends = new JSONArray(json);

            for (int i = 0; i < friends.length(); i++) {
                JSONObject friend = friends.getJSONObject(i);
                if ((friend.getInt("userId") == userId && friend.getInt("friendId") == friendId)
                        || (friend.getInt("userId") == friendId && friend.getInt("friendId") == userId)) {
                    friends.remove(i);
                    Files.write(Paths.get("friends.json"), friends.toString().getBytes());
                    System.out.println("Friend removed!");
                    return;
                }
            }

            System.out.println("Friend not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
