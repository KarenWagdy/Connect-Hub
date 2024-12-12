/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author nouri
 */
public class GroupEditing {

    /*
    public ImageIcon changeGroupPFP(File f) {
    ImageIcon profilePicture = new ImageIcon(f.getAbsolutePath());
    Image pfp = profilePicture.getImage();
    Image scaledPFP = pfp.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
    ImageIcon scaledIcon = new ImageIcon(scaledPFP);
    
    ArrayList<Group> pfpChange = GroupDatabase.readGroups();
    
    for (Group group : pfpChange) {
        if (group.getUserId() == Functionalities.currentUser.getUserId()) {
            user.setProfilePicture(f.getAbsolutePath());
            break; 
        }
    }
    
    UsersDatabase.saveUsers(pfpChange);
    
    return scaledIcon;
}

    public ImageIcon changeCoverPhoto(File f) {
   
      ImageIcon coverPicture = new ImageIcon(f.getAbsolutePath());
    Image cover = coverPicture.getImage();
    Image scaledCF = cover.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
    ImageIcon scaledIcon = new ImageIcon(scaledCF);
    
    ArrayList<User> coverChange = UsersDatabase.readUsers();
    
    for (User user : coverChange) {
        if (user.getUserId() == Functionalities.currentUser.getUserId()) {
            user.setCoverPicture(f.getAbsolutePath());
            break;
        }
    }
    
    UsersDatabase.saveUsers(coverChange);
    
    return scaledIcon;
    }

    public void changePassword(String password) {
        try {
            Functionalities.currentUser.setPassword(Functionalities.passwordHashing(password));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    
    ArrayList<User> userBio = UsersDatabase.readUsers();

    for (int i = 0; i < userBio.size(); i++) {
        if (userBio.get(i).getUserId()==(Functionalities.currentUser.getUserId())) {
            userBio.set(i, Functionalities.currentUser);  
            break;
        }
    }

    UsersDatabase.saveUsers(userBio);

    }

    public void changeBio(String bio) {
       
 ArrayList<User> userBio = UsersDatabase.readUsers();
    
    Functionalities.currentUser.setBio(bio);

    for (int i = 0; i < userBio.size(); i++) {
        if (userBio.get(i).getUserId()==(Functionalities.currentUser.getUserId())) {
            userBio.set(i, Functionalities.currentUser); 
            break;
        }
    }

    UsersDatabase.saveUsers(userBio);
    }

    static ArrayList<Post> postsArray = new ArrayList<>();

    public static ArrayList<Post> readPosts() {
        postsArray.clear();

        try {

            String jsonLines = new String(Files.readAllBytes(Paths.get("Posts.json")));
            JSONArray Posts = new JSONArray(jsonLines);

            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            for (int i = 0; i < Posts.length(); i++) {
                JSONObject post = Posts.getJSONObject(i);
                int contentId = post.getInt("contentId");
                int authortId = post.getInt("authorId");
                String content = post.getString("content");
                String imagePath = post.getString("image");
                LocalDateTime timeStamp = LocalDateTime.parse(post.getString("timestamp"), formatter);

                postsArray.add(new Post(authortId, content, imagePath, timeStamp));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return postsArray;
    }

    public static void savePosts(ArrayList<Post> post) {
        JSONArray postsArray = new JSONArray();
        for (Post i : post) {
            JSONObject obj = new JSONObject();
            obj.put("contentId", i.getContentId());
            obj.put("authorId", i.getAuthorId());
            obj.put("content", i.getContent());
            obj.put("image", i.getImagePath());
            obj.put("timestamp", i.getTimeStamp());
            postsArray.put(obj);

        }
        try {
            FileWriter file = new FileWriter("Posts.json");
            file.write(postsArray.toString(2));
            file.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public static ArrayList<Post> readPostsforUser(int userId) {
        ArrayList<Post> Allposts = readPosts();
        ArrayList<Post> userPosts = new ArrayList<>();
        for (Post post : Allposts) {
            if (post.getAuthorId() == userId) {
                userPosts.add(post);
            }
        }
        return userPosts;
    }*/
 /* public static ArrayList<Object> search(String name) {
    name = name.toLowerCase();
    
    // Fetch suggested users and groups
    ArrayList<User> users =UsersDatabase.readUsers();
    ArrayList<Group> groups =GroupDatabase.readGroups() ;
    
    ArrayList<Object> result = new ArrayList<>();
    
    // Search users
    for (User user : users) {
        if (user.getUsername().toLowerCase().contains(name) && 
            !user.getUsername().equalsIgnoreCase(Functionalities.currentUser.getUsername())) {
            result.add(user);
        }
    }
    
    // Search groups
    for (Group group : groups) {
        if (group.getName().toLowerCase().contains(name)) {
            result.add(group);
        }
    }
    
    return result;
}*/
    public static ArrayList<Object> search(String name) {
        name = name.toLowerCase();

        // Fetch suggested users and groups
        ArrayList<User> users = UsersDatabase.readUsers();
        ArrayList<Group> groups = GroupDatabase.readGroups();

        ArrayList<Object> result = new ArrayList<>();

        // Read pending membership requests
        ArrayList<Integer> pendingGroupIds = new ArrayList<>();
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("membershipRequest.json")));
            JSONArray membershipRequests = new JSONArray(jsonLines);

            for (int i = 0; i < membershipRequests.length(); i++) {
                JSONObject request = membershipRequests.getJSONObject(i);
                int userId = request.getInt("userId");
                int groupId = request.getInt("groupId");
                String status = request.getString("status");

                // If there is a pending request from the current user
                if (userId == Functionalities.currentUser.getUserId() && "Pending".equalsIgnoreCase(status)) {
                    pendingGroupIds.add(groupId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading membership requests.");
        }

        // Search users
        for (User user : users) {
            if (user.getUsername().toLowerCase().contains(name)
                    && !user.getUsername().equalsIgnoreCase(Functionalities.currentUser.getUsername())) {
                result.add(user);
            }
        }

        // Search groups, excluding groups with pending membership requests
        for (Group group : groups) {
            if (group.getName().toLowerCase().contains(name)
                    && !pendingGroupIds.contains(group.getGroupId())) {
                result.add(group);
            }
        }

        return result;
    }

    public static int validateGroupName(String name) {
        ArrayList<Group> groups = GroupDatabase.readGroups();
        for (Group g : groups) {
            if (g.getName().equalsIgnoreCase(name)) {
                return 1; // name already exists
            }
        }
        return 2; //valid group name
    }
   

}
