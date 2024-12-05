/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.time.LocalDateTime;

/**
 *
 * @author Alex
 */
public class Content {
    private int contentId;
    private int authorId;
    private String content;
    private String imagePath;
    private LocalDateTime timeStamp;
   public Content(){
       
   }//Parent class for posts and stories
    public Content( int authorId, String content, String imagePath, LocalDateTime timeStamp) {
        
        this.authorId = authorId;
        this.content = content;
        this.imagePath = imagePath;
        this.timeStamp = timeStamp;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
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
    
    
}
