/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.connecthub;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author karen
 */
public class ConnectHub {

   public static void main(String[] args) {
        ArrayList<Post> postsArray = new ArrayList<>();
       Post p1=  new Post(1,2,"HII","post.png",LocalDateTime.now());
       Post p2=new Post(1,2,"hello","image 2.png",LocalDateTime.now());
        postsArray.add(p1);
        postsArray.add(p2);
        PostDatabase.savePosts(postsArray);
        
        
    }
   
   
}
