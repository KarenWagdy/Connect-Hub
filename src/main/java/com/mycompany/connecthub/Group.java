/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.util.ArrayList;

/**
 *
 * @author nouri
 */
public class Group {
    private int groupId = 1 ;
    private String primaryAdmin;
    private ArrayList<User> admins ;
    private ArrayList<User> members;
    private String name;
    private String description;
    private String groupPhoto;

    public Group(ArrayList<User> admins, ArrayList<User> members, String primaryAdmin,String name, String description, String groupPhoto) {
        this.groupId=getMaxGroupId()+1;
        this.primaryAdmin = primaryAdmin;
        this.admins = admins;
        this.members = members;
        this.name = name;
        this.description = description;
        this.groupPhoto = groupPhoto;
    }
    public static int getMaxGroupId() {
        ArrayList<Group> group = GroupDatabase.groupsArray;
        if (group.isEmpty()) {
            return 0;
        } else {
            return group.get(group.size() - 1).getGroupId();
        }
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getPrimaryAdmin() {
        return primaryAdmin;
    }

    public void setPrimaryAdmin(String primaryAdmin) {
        this.primaryAdmin = primaryAdmin;
    }
    
    

   

    public ArrayList<User> getAdmins() {
        return admins;
    }

    public void setAdmins(ArrayList<User> admins) {
        this.admins = admins;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupPhoto() {
        return groupPhoto;
    }

    public void setGroupPhoto(String groupPhoto) {
        this.groupPhoto = groupPhoto;
    }
    
    
    
    
    
}
