/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author nouri
 */
public class FriendRequestManagement {

    
    public static ArrayList<User> search(String username) {
        username = username.toLowerCase();
        ArrayList<User> users = UsersDatabase.readUsers();
        ArrayList<User> result = new ArrayList<>();
        for (User user : users) {
            if (user.getUsername().toLowerCase().contains(username) && !user.getUsername().equalsIgnoreCase(Functionalities.currentUser.getUsername())) {
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


    
public static void acceptFriendRequest(int senderId, int receiverId) {
    try {
        String friendRequestJson = new String(Files.readAllBytes(Paths.get("friendRequest.json")));
        JSONArray friendRequests = new JSONArray(friendRequestJson);

        boolean requestFound = false;

        JSONArray friends = new JSONArray();

        for (int i = 0; i < friendRequests.length(); i++) {
            JSONObject request = friendRequests.getJSONObject(i);
            if (request.getInt("senderId") == senderId && request.getInt("receiverId") == receiverId) {
                JSONObject friend = new JSONObject();
                friend.put("senderId", senderId);
                friend.put("receiverId", receiverId);
                friends.put(friend);

                friendRequests.remove(i);
                requestFound = true;
                break;
            }
        }

        if (!requestFound) {
            System.out.println("Friend request not found!");
            return;
        }

        Files.write(Paths.get("friendRequest.json"), friendRequests.toString().getBytes());

        JSONArray existingFriends = new JSONArray();
        if (Files.exists(Paths.get("friend.json"))) {
            String friendJsonLines = new String(Files.readAllBytes(Paths.get("friend.json")));
            existingFriends = new JSONArray(friendJsonLines);
        }

        existingFriends.put(friends.getJSONObject(0)); 

        Files.write(Paths.get("friend.json"), existingFriends.toString().getBytes());

        System.out.println("Friend request accepted and moved to friends list!");
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
                    return;
                }
            }

            System.out.println("Friend not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> suggestFriends(int userId, ArrayList<Integer> allUserIds, ArrayList<Integer> friendIds, ArrayList<JSONObject> requests) {
        ArrayList<Integer> suggestions = new ArrayList<>(allUserIds);

        suggestions.remove(Integer.valueOf(userId));

        suggestions.removeAll(friendIds);

        for (JSONObject request : requests) {
            int senderId = request.getInt("senderId");
            int receiverId = request.getInt("receiverId");

            if (senderId == userId) {
                suggestions.remove(Integer.valueOf(receiverId));
            } else if (receiverId == userId) {
                suggestions.remove(Integer.valueOf(senderId));
            }
        }

        return suggestions;
    }

}
