/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import static com.mycompany.connecthub.PostDatabase.readPosts;
import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Alex
 */
// class that creates json file for stories and reads from it
public class StoryDatabase { 
    static ArrayList<Story> storiesArray = new ArrayList<>();
    private static StoryDatabase SD=null;
    private StoryDatabase()
    {
        
    }
    
    public static StoryDatabase getInstance()//create only one object of the class
    {
        
        if(SD==null)
        { //if SD is null create a new object
            SD=new StoryDatabase();
            
        }
        //if SD not equal to null return object
        return SD;
    }
    
// method to save story object in story json file
    public static void saveStories(ArrayList<Story> story) {
        JSONArray storiesArray = new JSONArray();
        for (Story i : story) {
            JSONObject obj = new JSONObject();
            obj.put("contentId", i.getContentId());
            obj.put("authorId", i.getAuthorId());
            obj.put("content", i.getContent());
            obj.put("image", i.getImagePath());
            obj.put("timestamp", i.getTimeStamp());
            
            System.out.println("Saving Story with Image Path: " + i.getImagePath());
            
            storiesArray.put(obj);
        }
        try {
            FileWriter file = new FileWriter("stories.json");
            file.write(storiesArray.toString(4));
            file.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
    //method to read story objects from story json file
   public static ArrayList<Story> readStories() {
        storiesArray.clear();
         
        try {
            
            String jsonLines = new String(Files.readAllBytes(Paths.get("stories.json")));
            JSONArray Stories = new JSONArray(jsonLines);

           
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            for (int i = 0; i < Stories.length(); i++) {
                JSONObject story = Stories.getJSONObject(i);
                int contentId = story.getInt("contentId");
                int authorId = story.getInt("authorId");
                String content=story.getString("content");
                String imagePath = story.has("image") ? story.getString("image") : null;
                LocalDateTime timeStamp = LocalDateTime.parse(story.getString("timestamp"), formatter);
               
                storiesArray.add(new Story( authorId, content,imagePath,timeStamp));
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return storiesArray;
}
   //method to delete stories after 24 hrs
    public static void deleteStories() {
        ArrayList<Story> stories = readStories();
        ArrayList<Story> updated = new ArrayList<>();
        for (Story s : stories) {
            Duration duration = Duration.between(s.getTimeStamp(), LocalDateTime.now());
            if (duration.toHours() < 24) {
                updated.add(s);
            }
        }
        saveStories(updated);
    }
    //method to choose image for a story from a selected file
    public ImageIcon chooseStoryImage(File f,User u)
    {
        ImageIcon storyPicture = new ImageIcon(f.getAbsolutePath());
        Image storyPic = storyPicture.getImage();
        Image scaledStoryPicture = storyPic.getScaledInstance(700, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledStoryPicture);
        //u.setCoverPhoto(f.getAbsoluteFile());
        
        return scaledIcon;
    }
    public static ArrayList<Story> readStoriesforUser(int userId)
    {
       ArrayList<Story> Allstories=readStories(); 
       ArrayList<Story>userStories=new ArrayList<>();
       for(Story story:Allstories)
       {
           if(story.getAuthorId()==userId)
           {
               userStories.add(story);
           }
       }
       return userStories;
    }
}
