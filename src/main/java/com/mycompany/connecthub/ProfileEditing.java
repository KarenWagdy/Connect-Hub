/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

/**
 *
 * @author X1
 */
import static com.mycompany.connecthub.Functionalities.passwordHashing;
import static com.mycompany.connecthub.Functionalities.usersArray;
import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProfileEditing {
     public ImageIcon changePFP(File f,User u)
    {
        ImageIcon profilePicture = new ImageIcon(f.getAbsolutePath());
        Image pfp = profilePicture.getImage();
        Image scaledPFP = pfp.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledPFP);
       // u.setpfp(f.getAbsoluteFile());
        return scaledIcon;
    }
    
    public ImageIcon changeCoverPhoto(File f,User u)
    {
        ImageIcon coverPicture = new ImageIcon(f.getAbsolutePath());
        Image coverPic = coverPicture.getImage();
        Image scaledCoverPicture = coverPic.getScaledInstance(700, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledCoverPicture);
        //u.setCoverPhoto(f.getAbsoluteFile());
        return scaledIcon;
    }
    
    public void changePassword(String password,User u)
    {
        
        u.setPassword(password);
    }
    
    public void changeBio(String bio,User u)
    {
       // u.setBio(bio);
    }
    
    static ArrayList<Post> postsArray = new ArrayList<>();
    
    public static ArrayList<Post> readPosts() 
    {
        postsArray.clear();
         
        try {
            
            String jsonLines = new String(Files.readAllBytes(Paths.get("Posts.json")));
            JSONArray Posts = new JSONArray(jsonLines);

            //DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            for (int i = 0; i < Posts.length(); i++) {
                JSONObject post = Posts.getJSONObject(i);
                int contentId = post.getInt("contentId");
                int authortId = post.getInt("authorId");
                String content=post.getString("content");
                String imagePath=post.getString("image");
                LocalDateTime timeStamp = LocalDateTime.parse(post.getString("timestamp"), formatter);
               
                postsArray.add(new Post(contentId, authortId, content,imagePath,timeStamp));
                
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
    
    
    
 
   /* public static void main(String args[])
    {
        ArrayList<Post> postsArray = new ArrayList<>();
       Post p1=  new Post(1,2,"HII","IMAGE 1.PNG",LocalDateTime.now());
       Post  p2=new Post(3,4,"hello","image 2.png");
        postsArray.add(p1);
        postsArray.add(p2);
        savePosts(postsArray);
        ArrayList<Post> p=readPostsforUser(5);
        
        for(int i=0;i<p.size();i++)
        {
            
            System.out.println(p.get(i).getContentId()+","+p.get(i).getAuthorId()+","
                    +p.get(i).getContent()+","+p.get(i).getImagePath()+","+p.get(i).getTimeStamp());
            
        }
        
        
        
        
        
    }*/

    
}
