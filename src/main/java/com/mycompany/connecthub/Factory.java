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
public class Factory {// applying factory design pattern
    //create content according to type 
    public Content createContent(String type) {
        if (type.equals("Post")) {
            return new Post();
        }
        if (type.equals("Story")) {
            return new Story();
        }
        return null;
    }
    public Content setContent(String type, int authorId, String content, String imagePath, LocalDateTime timeStamp) {
        // Create the appropriate Content object
        Content createdContent = createContent(type);

        if (createdContent != null) {
            // Set the attributes for the created content
            createdContent.setAuthorId(authorId);
            createdContent.setContent(content);
            createdContent.setImagePath(imagePath);
            createdContent.setTimeStamp(timeStamp);
            System.out.println("image="+imagePath);
            
            if (createdContent instanceof Post) {
                ((Post) createdContent).setContentId(Post.getMaxId() + 1);
                PostDatabase.postsArray.add((Post) createdContent); // Add to Post database
            }

            
            if (createdContent instanceof Story) {
                ((Story) createdContent).setContentId(Story.getMaxId() + 1);
                StoryDatabase.storiesArray.add((Story) createdContent); //add to story database
            }
        }
        return createdContent; // Return the created  Content object
    }
}
    

