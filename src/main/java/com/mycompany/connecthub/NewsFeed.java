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
   ArrayList<Notification> AllNotifications;
   ArrayList<Notification> GroupNotification;
   //ArrayList <Notification> AllNotification;
   Post p;
    /**
     * Creates new form NewsFeed
     */
    public NewsFeed() {
        initComponents();
        stories=StoryDatabase.readStories();
        posts=PostDatabase.readPosts();
        friends=FriendDatabase.readFriends(Functionalities.currentUser.getUserId());
        AllNotifications=FriendRequestNotificationDatabase.readFriendReqNotificationsForUser(Functionalities.currentUser.getUserId());
        AllNotifications.addAll(GroupActivitiesNotificationDatabase.readGroupNotificationsForUser(Functionalities.currentUser.getUserId()));
        AllNotifications.addAll(AddPostNotificationDatabase.readPostNotificationsForUser(Functionalities.currentUser.getUserId()));
        LoadFriendsPosts();
        LoadFriends();
        LoadFriendsStories();
        LoadFRNotifications();
        loadAllSuggestedGroups();
       // LoadGroupNotifications();

        
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
            listModel.addElement(friend.getUsername()+" ("+ friend.getStatus() + ") ");
            
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
    public void LoadFRNotifications() {
        //view notifications in NotificationList
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Notification notification : AllNotifications) {
            listModel.addElement(notification.getMessage());
            
        }
        
        NotificationList.setModel(listModel);

    }
    
    /*public void LoadGroupNotifications() {
        //view notifications in NotificationList
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Notification notification : GroupNotification) {
            listModel.addElement(notification.getMessage());
            
        }
        NotificationList.setModel(listModel);

    }*/
    
    public void loadAllSuggestedGroups() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        ArrayList<Group> suggested = GroupDatabase.suggestGroups();

        for (Group group : suggested) {
            listModel.addElement(group.getName());
        }

        groupsSuggestionsList.setModel(listModel);

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
        manageGroupsButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        manageFriendsButton1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        groupSuggestionsList = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        createGroupButton = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        NotificationList = new javax.swing.JList<>();
        jLabel5 = new javax.swing.JLabel();
        viewNotifications = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        groupsSuggestionsList = new javax.swing.JList<>();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("NewsFeed");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        createPostButton.setBackground(new java.awt.Color(51, 153, 255));
        createPostButton.setForeground(new java.awt.Color(255, 255, 255));
        createPostButton.setText("Create Post");
        createPostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPostButtonActionPerformed(evt);
            }
        });

        createStoriesButton.setBackground(new java.awt.Color(51, 153, 255));
        createStoriesButton.setForeground(new java.awt.Color(255, 255, 255));
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
        refreshButton.setForeground(new java.awt.Color(255, 255, 255));
        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        viewPosts.setBackground(new java.awt.Color(51, 153, 255));
        viewPosts.setForeground(new java.awt.Color(255, 255, 255));
        viewPosts.setText("View Posts");
        viewPosts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewPostsActionPerformed(evt);
            }
        });

        viewStoriesButton.setBackground(new java.awt.Color(51, 153, 255));
        viewStoriesButton.setForeground(new java.awt.Color(255, 255, 255));
        viewStoriesButton.setText("View Stories");
        viewStoriesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewStoriesButtonActionPerformed(evt);
            }
        });

        viewProfileButton.setBackground(new java.awt.Color(51, 153, 255));
        viewProfileButton.setForeground(new java.awt.Color(255, 255, 255));
        viewProfileButton.setText("View profile");
        viewProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProfileButtonActionPerformed(evt);
            }
        });

        manageGroupsButton.setBackground(new java.awt.Color(51, 153, 255));
        manageGroupsButton.setForeground(new java.awt.Color(255, 255, 255));
        manageGroupsButton.setText("Manage Groups");
        manageGroupsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageGroupsButtonActionPerformed(evt);
            }
        });

        logoutButton.setBackground(new java.awt.Color(51, 153, 255));
        logoutButton.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        manageFriendsButton1.setBackground(new java.awt.Color(51, 153, 255));
        manageFriendsButton1.setForeground(new java.awt.Color(255, 255, 255));
        manageFriendsButton1.setText("Manage friends");
        manageFriendsButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageFriendsButton1ActionPerformed(evt);
            }
        });

        jScrollPane4.setViewportView(groupSuggestionsList);

        jLabel4.setText("Groups");

        createGroupButton.setBackground(new java.awt.Color(51, 153, 255));
        createGroupButton.setForeground(new java.awt.Color(255, 255, 255));
        createGroupButton.setText("Create Group");
        createGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createGroupButtonActionPerformed(evt);
            }
        });

        jScrollPane5.setViewportView(NotificationList);

        jLabel5.setText("Notifications");

        viewNotifications.setBackground(new java.awt.Color(51, 153, 255));
        viewNotifications.setForeground(new java.awt.Color(255, 255, 255));
        viewNotifications.setText("View Notifications");
        viewNotifications.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewNotificationsActionPerformed(evt);
            }
        });

        jScrollPane6.setViewportView(groupsSuggestionsList);

        jLabel6.setText("Groups Suggestions");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(102, 102, 102)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewPosts, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(263, 263, 263)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(viewStoriesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(42, 42, 42))
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(viewNotifications, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(refreshButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(manageGroupsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(viewProfileButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(manageFriendsButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(createStoriesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(createPostButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(createGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(viewStoriesButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(viewPosts)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addComponent(jLabel4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(viewNotifications))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(createPostButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(createStoriesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(viewProfileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(manageFriendsButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(manageGroupsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(createGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(refreshButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(85, Short.MAX_VALUE))
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
   
    // Clear current lists
    ((DefaultListModel<String>) postsList.getModel()).removeAllElements();
    ((DefaultListModel<String>) storiesList.getModel()).removeAllElements();
    ((DefaultListModel<String>) friendsList.getModel()).removeAllElements();

    // Reload posts, stories, and friends
    LoadFriendsPosts();
    LoadFriendsStories();
    LoadFriends();


    
    
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void manageGroupsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageGroupsButtonActionPerformed
        GroupManagementWindow gmw = new GroupManagementWindow();
        this.setVisible(false);
        gmw.setVisible(true);
    }//GEN-LAST:event_manageGroupsButtonActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
    }//GEN-LAST:event_formWindowClosed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
         ArrayList<User> userStatus = UsersDatabase.readUsers();
    
        Functionalities.currentUser.setStatus("offline");
    for (int i = 0; i < userStatus.size(); i++) {
        if (userStatus.get(i).getUserId()==(Functionalities.currentUser.getUserId())) {
            userStatus.set(i, Functionalities.currentUser); 
            break;
        }
    }

    UsersDatabase.saveUsers(userStatus);
       
        this.setVisible(false);
        SignupLoginWindow slw = SignupLoginWindow.getInstance();
        slw.setVisible(true);
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void manageFriendsButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageFriendsButton1ActionPerformed
        this.setVisible(false);
        FriendRequestWindow frw = new FriendRequestWindow();
        frw.setVisible(true);
    }//GEN-LAST:event_manageFriendsButton1ActionPerformed

    private void createGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createGroupButtonActionPerformed
        CreateGroupWindow cgw = new CreateGroupWindow();
        this.setVisible(false);
        cgw.setVisible(true);
    }//GEN-LAST:event_createGroupButtonActionPerformed

    private void viewNotificationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewNotificationsActionPerformed
        // TODO add your handling code here:
        DefaultListModel<String> listModel1;
         int index = NotificationList.getSelectedIndex();
         String selectedRequest = NotificationList.getSelectedValue();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Choose a notification");
        } else {
            
            Notification n = AllNotifications.get(index);
            if(n instanceof FriendRequestNotification)
            {
               int senderId= ((FriendRequestNotification) n).getSenderId();
                FriendReqNotificationWindow w=new FriendReqNotificationWindow(senderId,n);
               // this.setVisible(false);
                w.setVisible(true);
                listModel1 = (DefaultListModel<String>) NotificationList.getModel();
                listModel1.remove(index);
                AllNotifications.remove(index);
                FriendRequestNotificationDatabase.saveFriendReqNotifications(AllNotifications);
            } 
            if(n instanceof GroupActivitiesNotification){
                Group group=GroupDatabase.getGroup(((GroupActivitiesNotification) n).getGroupId());
                UserGroupProfile groupProfile=new UserGroupProfile(group);
                groupProfile.setVisible(true);
                GroupActivitiesNotificationDatabase.saveGroupNotifications(AllNotifications);
                
            }
            if(n instanceof AddPostNotification){
                Group group=GroupDatabase.getGroup(((GroupActivitiesNotification) n).getGroupId());
                if(Functionalities.currentUser.getUsername().equals(group.getPrimaryAdmin())){
                    AdminGroupProfile adminProfile=new AdminGroupProfile(group);
                    adminProfile.setVisible(true);
                    AddPostNotificationDatabase.saveGroupPostsNotifications(AllNotifications);
                }else{
                UserGroupProfile groupProfile=new UserGroupProfile(group);
                groupProfile.setVisible(true);
                GroupActivitiesNotificationDatabase.saveGroupNotifications(AllNotifications);
                }
            }
            
            
        }
        
    }//GEN-LAST:event_viewNotificationsActionPerformed

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
    private javax.swing.JList<String> NotificationList;
    private javax.swing.JButton createGroupButton;
    private javax.swing.JButton createPostButton;
    private javax.swing.JButton createStoriesButton;
    private javax.swing.JList<String> friendsList;
    private javax.swing.JList<String> groupSuggestionsList;
    private javax.swing.JList<String> groupsSuggestionsList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton manageFriendsButton1;
    private javax.swing.JButton manageGroupsButton;
    private javax.swing.JList<String> postsList;
    private javax.swing.JButton refreshButton;
    private javax.swing.JList<String> storiesList;
    private javax.swing.JButton viewNotifications;
    private javax.swing.JButton viewPosts;
    private javax.swing.JButton viewProfileButton;
    private javax.swing.JButton viewStoriesButton;
    // End of variables declaration//GEN-END:variables
}
