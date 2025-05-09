/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author X1
 */
public class Post extends Content {
    private int contentId=1;
    //subclass of content parent class
    public Post(){
        
    }
    
    public Post( int authorId, String content, String imagePath, LocalDateTime timeStamp) {
       
        super( authorId, content, imagePath, timeStamp);
         this.contentId = getMaxId()+1;
    }
    //method to increment contentId in Json file once a post is created
     public static int getMaxId()
    {
        ArrayList<Post> post=PostDatabase.postsArray;
        if(post.isEmpty())
        {
            return 0;
        }
        else 
        {
            return post.get(post.size()-1).getContentId();
        }
    }
    
}
