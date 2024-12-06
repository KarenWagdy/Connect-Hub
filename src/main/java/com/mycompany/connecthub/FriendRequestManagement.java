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

    public static ArrayList<User> search(String username) {
        username = username.toLowerCase();
        ArrayList<User> users = suggestFriends();
        ArrayList<User> result = new ArrayList<>();
        for (User user : users) {
            if (user.getUsername().toLowerCase().contains(username) && !user.getUsername().equalsIgnoreCase(Functionalities.currentUser.getUsername())) {
                result.add(user);
            }
        }
        return result;
    }

    public static void blockUser(int blockedUserId) {
        try {
            JSONArray blockedUsers = new JSONArray();
            if (Files.exists(Paths.get("blockedUsers.json"))) {
                String json = new String(Files.readAllBytes(Paths.get("blockedUsers.json")));
                blockedUsers = new JSONArray(json);
            }

            JSONObject newBlock = new JSONObject();
            newBlock.put("userId", Functionalities.currentUser.getUserId());
            newBlock.put("blockedUserId", blockedUserId);

            blockedUsers.put(newBlock);

            Files.write(Paths.get("blockedUsers.json"), blockedUsers.toString(2).getBytes()); 

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

    public static ArrayList<User> suggestFriends() {
        ArrayList<User> allUsers = UsersDatabase.readUsers(); 
        ArrayList<User> suggestions = new ArrayList<>();

        try {
            ArrayList<Integer> friendIds = new ArrayList<>();
            if (Files.exists(Paths.get("Friends.json"))) {
                String friendsJson = new String(Files.readAllBytes(Paths.get("Friends.json")));
                JSONArray friendsArray = new JSONArray(friendsJson);

                for (int i = 0; i < friendsArray.length(); i++) {
                    JSONObject friend = friendsArray.getJSONObject(i);
                    int user1Id = friend.getInt("user1Id");
                    int user2Id = friend.getInt("user2Id");

                    if (user1Id == Functionalities.currentUser.getUserId()) {
                        friendIds.add(user2Id);
                    } else if (user2Id == Functionalities.currentUser.getUserId()) {
                        friendIds.add(user1Id);
                    }
                }
            }

            ArrayList<Integer> blockedUserIds = new ArrayList<>();
            if (Files.exists(Paths.get("blockedUsers.json"))) {
                String blockedJson = new String(Files.readAllBytes(Paths.get("blockedUsers.json")));
                JSONArray blockedArray = new JSONArray(blockedJson);

                for (int i = 0; i < blockedArray.length(); i++) {
                    JSONObject block = blockedArray.getJSONObject(i);
                    int userId = block.getInt("userId");
                    int blockedUserId = block.getInt("blockedUserId");

                    if (userId == Functionalities.currentUser.getUserId()) {
                        blockedUserIds.add(blockedUserId);
                    } else if (blockedUserId == Functionalities.currentUser.getUserId()) {
                        blockedUserIds.add(userId);
                    }
                }
            }

            ArrayList<Integer> pendingRequestIds = new ArrayList<>();
            if (Files.exists(Paths.get("friendRequest.json"))) {
                String requestsJson = new String(Files.readAllBytes(Paths.get("friendRequest.json")));
                JSONArray requestsArray = new JSONArray(requestsJson);

                for (int i = 0; i < requestsArray.length(); i++) {
                    JSONObject request = requestsArray.getJSONObject(i);
                    int senderId = request.getInt("senderId");
                    int receiverId = request.getInt("receiverId");

                    if (senderId == Functionalities.currentUser.getUserId()) {
                        pendingRequestIds.add(receiverId);
                    } else if (receiverId == Functionalities.currentUser.getUserId()) {
                        pendingRequestIds.add(senderId);
                    }
                }
            }

            for (User user : allUsers) {
                int userId = user.getUserId();
                if (userId != Functionalities.currentUser.getUserId() && !friendIds.contains(userId) && !blockedUserIds.contains(userId) && !pendingRequestIds.contains(userId)) {
                    suggestions.add(user);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while fetching suggested friends.");
        }

        return suggestions;
    }

}
