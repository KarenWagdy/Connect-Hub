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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author X1
 */
public class PostDatabase {
    static ArrayList<Post> postsArray = new ArrayList<>();
    
    private static PostDatabase PD=null;
    private PostDatabase()
    {
        
    }
    
    public static PostDatabase getInstance()//create only one object of the class
    {
        
        if(PD==null)
        { //if PD is null create a new object
            PD=new PostDatabase();
            
        }
        //if PD not equal to null return object
        return PD;
    }
    
    //read posts found in post.json
    public static ArrayList<Post> readPosts() 
    {
        postsArray.clear();
         
        try {
            
            String jsonLines = new String(Files.readAllBytes(Paths.get("Posts.json")));
            JSONArray Posts = new JSONArray(jsonLines);

            
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            for (int i = 0; i < Posts.length(); i++) {
                JSONObject post = Posts.getJSONObject(i);
                int contentId = post.getInt("contentId");
                int authortId = post.getInt("authorId");
                String content=post.getString("content");
                String imagePath=post.getString("image");
                LocalDateTime timeStamp = LocalDateTime.parse(post.getString("timestamp"), formatter);
               //add an object of type post to the arraylist
                postsArray.add(new Post( authortId, content,imagePath,timeStamp));
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return postsArray;
    }
    
 
    //save objects of type post
    public static void savePosts(ArrayList<Post> post) {
        JSONArray postsArray = new JSONArray();
        //write the attributes of post object in post.json file
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
    //read all posts for specific user using userId
    public static ArrayList<Post> readPostsforUser(int userId)
    {
       ArrayList<Post> Allposts=readPosts(); 
       ArrayList<Post>userPosts=new ArrayList<>();
       for(Post post:Allposts)
       {
           if(post.getAuthorId()==userId)
           {
               userPosts.add(post);
           }
       }
       return userPosts;
    }
    //method to select an image from specific file and add it to the post
    public ImageIcon choosePostImage(File f,User u)
    {
        ImageIcon postPicture = new ImageIcon(f.getAbsolutePath());
        Image postPic = postPicture.getImage();
        Image scaledPostPicture = postPic.getScaledInstance(700, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledPostPicture);
      
        return scaledIcon;
    }
}
