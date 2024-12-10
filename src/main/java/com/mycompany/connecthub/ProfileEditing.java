/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

/**
 *
 * @author X1
 */

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProfileEditing {

    public ImageIcon changePFP(File f) {
    ImageIcon profilePicture = new ImageIcon(f.getAbsolutePath());
    Image pfp = profilePicture.getImage();
    Image scaledPFP = pfp.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
    ImageIcon scaledIcon = new ImageIcon(scaledPFP);
    
    ArrayList<User> pfpChange = UsersDatabase.readUsers();
    
    for (User user : pfpChange) {
        if (user.getUserId() == Functionalities.currentUser.getUserId()) {
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
    }

}
