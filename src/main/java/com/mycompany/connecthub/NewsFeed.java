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
        //posts=FriendsPosts.getPostsByFriendId();
        //friends=FriendsPosts.friends;
        LoadFriendsPosts();
       // LoadFriends();
        LoadFriendsStories();
        
    }

     public void LoadFriendsPosts() {
        //view posts in postsList
        DefaultListModel<String> listModel = new DefaultListModel<>();
        
        for (Post post : posts) {
            listModel.addElement(post.getContent());
        }
        postsList.setModel(listModel);
    }
     /* public void LoadFriends() {
        //view friends in FriendList
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (User friend : friends) {
            listModel.addElement(friend.getUsername());
        }
        friendsList.setModel(listModel);

    }*/
    public void LoadFriendsStories() {
        //view posts in postsList
        DefaultListModel<String> listModel = new DefaultListModel<>();
        StoryDatabase.deleteStories();
        for (Story story : stories) {
            listModel.addElement(story.getContent());
            
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
        deleteButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("NewsFeed");

        createPostButton.setText("Create Post");
        createPostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPostButtonActionPerformed(evt);
            }
        });

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

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        viewPosts.setText("View Posts");
        viewPosts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewPostsActionPerformed(evt);
            }
        });

        viewStoriesButton.setText("View Stories");
        viewStoriesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewStoriesButtonActionPerformed(evt);
            }
        });

        viewProfileButton.setText("View profile");
        viewProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProfileButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
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
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(createStoriesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(createPostButton)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel3)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(viewProfileButton)
                                    .addComponent(viewPosts)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(171, 171, 171)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 153, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(112, 112, 112))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(66, 66, 66))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(viewStoriesButton)
                            .addContainerGap()))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(refreshButton)
                        .addGap(41, 41, 41))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(createPostButton)
                        .addGap(18, 18, 18)
                        .addComponent(createStoriesButton)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(viewPosts))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(viewStoriesButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(viewProfileButton)
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(refreshButton)
                            .addComponent(deleteButton))
                        .addGap(67, 67, 67))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createStoriesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createStoriesButtonActionPerformed
        StoriesCreation story=new StoriesCreation(); 
        story.setVisible(true);
        
    }//GEN-LAST:event_createStoriesButtonActionPerformed

    private void createPostButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPostButtonActionPerformed
        PostsCreation post=new PostsCreation(); 
        post.setVisible(true);
        
    }//GEN-LAST:event_createPostButtonActionPerformed

    private void viewPostsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewPostsActionPerformed
        
           int index = postsList.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Choose post");
        } else {
            Post post = posts.get(index);
            ViewPosts view = new ViewPosts(post);
            view.setVisible(true);
        }
           
    }//GEN-LAST:event_viewPostsActionPerformed

    private void viewProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProfileButtonActionPerformed
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
            ViewStories view = new ViewStories(story);
            view.setVisible(true);
        }
    }//GEN-LAST:event_viewStoriesButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
    DefaultListModel<String> model1 = (DefaultListModel<String>) postsList.getModel();
    DefaultListModel<String> model2 = (DefaultListModel<String>) storiesList.getModel();   
   
    
    // Reload posts and stories
    LoadFriendsPosts();
    LoadFriendsStories();
    

    
    
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
       DefaultListModel<String> model1 = (DefaultListModel<String>) postsList.getModel();
       DefaultListModel<String> model2 = (DefaultListModel<String>) storiesList.getModel();
       StoryDatabase storydb;
        // Check if the JLists is not empty, then clear it
        if (model1.getSize() > 0) {
        model1.removeAllElements(); // Clears all elements from the postslist
    } if (model2.getSize() > 0) {
        model2.removeAllElements(); // Clears all elements from the storieslist
    }

        
    }//GEN-LAST:event_deleteButtonActionPerformed

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
    private javax.swing.JButton deleteButton;
    private javax.swing.JList<String> friendsList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> postsList;
    private javax.swing.JButton refreshButton;
    private javax.swing.JList<String> storiesList;
    private javax.swing.JButton viewPosts;
    private javax.swing.JButton viewProfileButton;
    private javax.swing.JButton viewStoriesButton;
    // End of variables declaration//GEN-END:variables
}
