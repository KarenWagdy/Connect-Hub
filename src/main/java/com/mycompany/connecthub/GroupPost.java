/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author nouri
 */
public class GroupPost {
    private int groupId;
    private int authorId;
    private int contentId=1;
    private String content;
    private String imagePath;
    private LocalDateTime timeStamp;

    public GroupPost(int groupId, int authorId,String content, String imagePath, LocalDateTime timeStamp) {
        this.groupId = groupId;
        this.authorId = authorId;
        this.contentId= getMaxId()+1;
        this.content = content;
        this.imagePath = imagePath;
        this.timeStamp = timeStamp;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
    

        
    
    
     public static int getMaxId()
    {
        ArrayList<GroupPost> post=GroupPostDatabase.groupPostsArray;
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
