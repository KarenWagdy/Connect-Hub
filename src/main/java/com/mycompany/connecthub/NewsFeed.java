/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.connecthub;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Alex
 */
public class NewsFeed extends javax.swing.JFrame {
   ArrayList<Post> posts;
   ArrayList<User> friends;
   ArrayList<Story> stories;
   Post p;
    /**
     * Creates new form NewsFeed
     */
    public NewsFeed() {
        initComponents();
        stories=StoryDatabase.readStories();
        posts=PostDatabase.readPosts();
        friends=FriendDatabase.readFriends(Functionalities.currentUser.getUserId());
        //posts=FriendsPosts.getPostsByFriendId();
        //friends=FriendsPosts.friends;
        LoadFriendsPosts();
        LoadFriends();
        LoadFriendsStories();
        
    }

     public void LoadFriendsPosts() {
        //view posts in postsList
        DefaultListModel<String> listModel = new DefaultListModel<>();
        
        for (Post post : posts) {
            if(FriendDatabase.isFriend(post.getAuthorId())){
            listModel.addElement(post.getContent());
            }
        }
        postsList.setModel(listModel);
    }
     
      public void LoadFriends() {
        //view friends in FriendList
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (User friend : friends) {
           //if(FriendDatabase.isFriends(Functionalities.currentUser.getUserId())){
            listModel.addElement(friend.getUsername());
            //}
        }
        friendsList.setModel(listModel);

    }
      
    public void LoadFriendsStories() {
        StoryDatabase.deleteStories();

    // Refresh the stories list
        stories = StoryDatabase.readStories();
        //view posts in postsList
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Story story : stories) {
            if(FriendDatabase.isFriend(story.getAuthorId())){
            listModel.addElement(story.getContent());
            }
        }
        storiesList.setModel(listModel);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        createPostButton = new javax.swing.JButton();
        createStoriesButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        postsList = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        storiesList = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        friendsList = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        refreshButton = new javax.swing.JButton();
        viewPosts = new javax.swing.JButton();
        viewStoriesButton = new javax.swing.JButton();
        viewProfileButton = new javax.swing.JButton();
        manageFriendsButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("NewsFeed");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        createPostButton.setBackground(new java.awt.Color(51, 153, 255));
        createPostButton.setText("Create Post");
        createPostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPostButtonActionPerformed(evt);
            }
        });

        createStoriesButton.setBackground(new java.awt.Color(51, 153, 255));
        createStoriesButton.setText("Create Story");
        createStoriesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createStoriesButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(postsList);

        jScrollPane2.setViewportView(storiesList);

        jLabel1.setText("Posts List");

        jLabel2.setText("Stories List");

        jScrollPane3.setViewportView(friendsList);

        jLabel3.setText("Friends List");

        refreshButton.setBackground(new java.awt.Color(51, 153, 255));
        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        viewPosts.setBackground(new java.awt.Color(51, 153, 255));
        viewPosts.setText("View Posts");
        viewPosts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewPostsActionPerformed(evt);
            }
        });

        viewStoriesButton.setBackground(new java.awt.Color(51, 153, 255));
        viewStoriesButton.setText("View Stories");
        viewStoriesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewStoriesButtonActionPerformed(evt);
            }
        });

        viewProfileButton.setBackground(new java.awt.Color(51, 153, 255));
        viewProfileButton.setText("View profile");
        viewProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProfileButtonActionPerformed(evt);
            }
        });

        manageFriendsButton.setBackground(new java.awt.Color(51, 153, 255));
        manageFriendsButton.setText("Manage friends");
        manageFriendsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageFriendsButtonActionPerformed(evt);
            }
        });

        logoutButton.setBackground(new java.awt.Color(51, 153, 255));
        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel3)
                                .addGap(86, 86, 86)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(viewPosts, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(48, 48, 48)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 2, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(createPostButton)
                        .addGap(18, 18, 18)
                        .addComponent(createStoriesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(viewProfileButton)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(183, 183, 183)
                                .addComponent(manageFriendsButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewStoriesButton))))
                .addGap(49, 49, 49))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(viewPosts)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(viewStoriesButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(refreshButton)
                            .addComponent(viewProfileButton)
                            .addComponent(createStoriesButton)
                            .addComponent(createPostButton)
                            .addComponent(manageFriendsButton))
                        .addGap(67, 67, 67))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createStoriesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createStoriesButtonActionPerformed
        this.setVisible(false);
        StoriesCreation story=new StoriesCreation(); 
        story.setVisible(true);
    }//GEN-LAST:event_createStoriesButtonActionPerformed

    private void createPostButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPostButtonActionPerformed
        this.setVisible(false);
        PostsCreation post=new PostsCreation(); 
        post.setVisible(true);
        
    }//GEN-LAST:event_createPostButtonActionPerformed

    private void viewPostsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewPostsActionPerformed
        
           int index = postsList.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Choose post");
        } else {
            Post post = posts.get(index);
            this.setVisible(false);
            ViewPosts view = new ViewPosts(post);
            view.setVisible(true);
        }
           
    }//GEN-LAST:event_viewPostsActionPerformed

    private void viewProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProfileButtonActionPerformed
        this.setVisible(false);
        UserProfile userprofile=new UserProfile();
        userprofile.setVisible(true);
    }//GEN-LAST:event_viewProfileButtonActionPerformed

    private void viewStoriesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewStoriesButtonActionPerformed
        // TODO add your handling code here:
        int index = storiesList.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Choose story");
        } else {
            Story story = stories.get(index);
            this.setVisible(false);
            ViewStories view = new ViewStories(story);
            view.setVisible(true);
        }
    }//GEN-LAST:event_viewStoriesButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
   
    /*DefaultListModel<String> model1 = (DefaultListModel<String>) postsList.getModel();
    DefaultListModel<String> model2 = (DefaultListModel<String>) storiesList.getModel();
    DefaultListModel<String> model3 = (DefaultListModel<String>) friendsList.getModel();
    
    StoryDatabase.deleteStories();
    
    if (model1.getSize() > 0) {
        model1.removeAllElements(); //Clear from postslist
    } if (model2.getSize() > 0) {
        model2.removeAllElements(); //Clear from storiesslist
    }
    if (model3.getSize() > 0) {
        model3.removeAllElements(); //Clear from friendslist
    }

    
    // Reload posts and stories and friends
    LoadFriendsPosts();
    LoadFriendsStories();
    LoadFriends() ;
    
    */
    
    // Clear current lists
    ((DefaultListModel<String>) postsList.getModel()).clear();
    ((DefaultListModel<String>) storiesList.getModel()).clear();
    ((DefaultListModel<String>) friendsList.getModel()).clear();

    // Reload posts, stories, and friends
    LoadFriendsPosts();
    LoadFriendsStories();
    LoadFriends();


    
    
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void manageFriendsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageFriendsButtonActionPerformed
        this.setVisible(false);
        FriendRequestWindow frw = new FriendRequestWindow();
        frw.setVisible(true);
    }//GEN-LAST:event_manageFriendsButtonActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
    }//GEN-LAST:event_formWindowClosed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        Functionalities.currentUser.setStatus("offline");
        this.setVisible(false);
        SignupLoginWindow slw = SignupLoginWindow.getInstance();
        slw.setVisible(true);
    }//GEN-LAST:event_logoutButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewsFeed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewsFeed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewsFeed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewsFeed.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewsFeed().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createPostButton;
    private javax.swing.JButton createStoriesButton;
    private javax.swing.JList<String> friendsList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton manageFriendsButton;
    private javax.swing.JList<String> postsList;
    private javax.swing.JButton refreshButton;
    private javax.swing.JList<String> storiesList;
    private javax.swing.JButton viewPosts;
    private javax.swing.JButton viewProfileButton;
    private javax.swing.JButton viewStoriesButton;
    // End of variables declaration//GEN-END:variables
}
