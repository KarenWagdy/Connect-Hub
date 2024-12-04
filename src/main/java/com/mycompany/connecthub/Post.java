/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author X1
 */
public class Post {
    private int contentId;
    private int authorId;
    private String content;
    private String imagePath;
    private LocalDateTime timeStamp;

    public Post(int contentId, int authorId, String content,String imagePath) {
        this.contentId = contentId;
        this.authorId = authorId;
        this.content = content;
        this.imagePath=imagePath;
        this.timeStamp = LocalDateTime.now(); //Timestamp when creating a post
    }
    
    public Post(int contentId, int authorId, String content,String imagePath, LocalDateTime timeStamp) {
        this.contentId = contentId;
        this.authorId = authorId;
        this.content = content;
        this.imagePath=imagePath;
        this.timeStamp = timeStamp; // Timestamp read from JSON file
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

    

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    

    
}
