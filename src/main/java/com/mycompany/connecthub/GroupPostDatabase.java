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
 * @author nouri
 */
public class GroupPostDatabase {
        static ArrayList<GroupPost> groupPostsArray = new ArrayList<>();
        
            private static GroupPostDatabase GPD=null;

        
        public static GroupPostDatabase getInstance()//create only one object of the class
    {
        
        if(GPD==null)
        { //if PD is null create a new object
            GPD=new GroupPostDatabase();
            
        }
        //if PD not equal to null return object
        return GPD;
    }
    


    public GroupPostDatabase() {
    }
    public static ArrayList<GroupPost> readGroupPosts() 
    {
        groupPostsArray.clear();
         
        try {
            
            String jsonLines = new String(Files.readAllBytes(Paths.get("groupPosts.json")));
            JSONArray Posts = new JSONArray(jsonLines);

            
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            for (int i = 0; i < Posts.length(); i++) {
                JSONObject post = Posts.getJSONObject(i);
                int contentId = post.getInt("contentId");
                int authortId = post.getInt("authorId");
                int groupId =post.getInt("groupId");
                String content=post.getString("content");
                String imagePath=post.getString("image");
                LocalDateTime timeStamp = LocalDateTime.parse(post.getString("timestamp"), formatter);
               //add an object of type post to the arraylist
                groupPostsArray.add(new GroupPost( groupId, authortId, content ,imagePath,timeStamp));
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return groupPostsArray;
    }
    
 
    public static void savePostsForGroup(ArrayList<GroupPost> post) {
        JSONArray postsArray = new JSONArray();
        for (GroupPost i : post) {
            JSONObject obj = new JSONObject();
            obj.put("groupId", i.getGroupId());
            obj.put("contentId", i.getContentId());
            obj.put("authorId", i.getAuthorId());
            obj.put("content", i.getContent());
            obj.put("image", i.getImagePath());
            obj.put("timestamp", i.getTimeStamp());
            postsArray.put(obj);
      
        }
        try {
            FileWriter file = new FileWriter("groupPosts.json");
            file.write(postsArray.toString(2));
            file.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
    public static ArrayList<GroupPost> readPostsforGroup(int groupId)
    {
       ArrayList<GroupPost> allGroupPosts=readGroupPosts(); 
       ArrayList<GroupPost> groupPosts=new ArrayList<>();
       for(GroupPost post:allGroupPosts)
       {
           if(post.getGroupId()==groupId)
           {
               groupPosts.add(post);
           }
       }
       return groupPosts;
    }
    public ImageIcon choosePostImage(File f,Group g)
    {
        ImageIcon postPicture = new ImageIcon(f.getAbsolutePath());
        Image postPic = postPicture.getImage();
        Image scaledPostPicture = postPic.getScaledInstance(700, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledPostPicture);
      
        return scaledIcon;
    }
    
    
}
