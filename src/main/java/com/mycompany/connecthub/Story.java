/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import static com.mycompany.connecthub.Post.getMaxId;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Alex
 */
//story class is subclass of content class
public class Story extends Content {
    private int contentId=1;
    public Story(){
        
    }
    
    public Story( int authorId, String content, String imagePath, LocalDateTime timeStamp) {
        super( authorId, content, imagePath, timeStamp);
        this.contentId = getMaxId()+1;
    }
    //method to increment content id once a story created
     public static int getMaxId()
    {
        ArrayList<Story> story=StoryDatabase.storiesArray;
        if(story.isEmpty())
        {
            return 0;
        }
        else 
        {
            return story.get(story.size()-1).getContentId();
        }
    }
}
