/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import static com.mycompany.connecthub.FriendDatabase.FriendsArray;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author nouri
 */
public class GroupDatabase {

    private static GroupDatabase groupDatabase = null;

    private GroupDatabase() {
    }

    public static GroupDatabase getInstance() {
        if (groupDatabase == null) {
            groupDatabase = new GroupDatabase();
        }
        return groupDatabase;  // if group JSon File database is null make an object of it else return the same object
    }

    static ArrayList<Group> groupsArray = new ArrayList<>();

    public static ArrayList<Group> readGroups() {
        groupsArray.clear();
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("groups.json")));
            JSONArray groups = new JSONArray(jsonLines);

            for (int i = 0; i < groups.length(); i++) {
                JSONObject groupJson = groups.getJSONObject(i);
                int groupId = groupJson.getInt("groupId");
                String name = groupJson.getString("name");
                String groupPhoto = groupJson.getString("photo");
                String description = groupJson.getString("description");
                String primaryAdminUsername = groupJson.getString("primaryAdmin");
                User primaryAdmin = Functionalities.getUser(primaryAdminUsername);

                JSONArray adminsJsonArray = groupJson.getJSONArray("admins");
                ArrayList<User> admins = new ArrayList<>();
                for (int j = 0; j < adminsJsonArray.length(); j++) {
                    String adminUsername = adminsJsonArray.getString(j);
                    User admin = Functionalities.getUser(adminUsername);
                    admins.add(admin);
                }

                JSONArray membersJsonArray = groupJson.getJSONArray("members");
                ArrayList<User> members = new ArrayList<>();
                for (int j = 0; j < membersJsonArray.length(); j++) {
                    String memberUsername = membersJsonArray.getString(j);
                    User member = Functionalities.getUser(memberUsername);
                    members.add(member);
                }

                groupsArray.add(new Group(admins, members, primaryAdminUsername, name, description, groupPhoto));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return groupsArray;
    }

    public static void saveGroups(ArrayList<Group> groups) {
        try {
            JSONArray groupsJsonArray = new JSONArray();

            for (Group group : groups) {
                JSONObject groupJson = new JSONObject();
                groupJson.put("groupId", group.getGroupId());
                groupJson.put("name", group.getName());
                groupJson.put("photo", group.getGroupPhoto());
                groupJson.put("description", group.getDescription());
                groupJson.put("primaryAdmin", group.getPrimaryAdmin());

                JSONArray adminsJsonArray = new JSONArray();
                for (User admin : group.getAdmins()) {
                    adminsJsonArray.put(admin.getUsername());
                }
                groupJson.put("admins", adminsJsonArray);

                JSONArray membersJsonArray = new JSONArray();
                for (User member : group.getMembers()) {
                    membersJsonArray.put(member.getUsername());
                }
                groupJson.put("members", membersJsonArray);

                groupsJsonArray.put(groupJson);
            }

            Files.write(Paths.get("groups.json"), groupsJsonArray.toString(4).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Group> readGroupsForUser(int userId) {
        ArrayList<Group> userGroups = new ArrayList<>();

        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("groups.json")));
            JSONArray groupsArray = new JSONArray(jsonLines);
            String currentUsername = Functionalities.getUser(userId).getUsername(); // Get username once
            System.out.println(currentUsername);


            for (int i = 0; i < groupsArray.length(); i++) {
                JSONObject groupJson = groupsArray.getJSONObject(i);

                int groupId = groupJson.getInt("groupId");
                String name = groupJson.getString("name");
                String description = groupJson.getString("description");
                String groupPhoto = groupJson.getString("photo");
                String primaryAdmin = groupJson.getString("primaryAdmin");

                JSONArray adminsJsonArray = groupJson.getJSONArray("admins");
                JSONArray membersJsonArray = groupJson.getJSONArray("members");

                // Check if userId is in admins or members list
                boolean isUserInGroup = adminsJsonArray.toList().contains(Functionalities.getUser(userId).getUsername()) || membersJsonArray.toList().contains(Functionalities.getUser(userId).getUsername()) || primaryAdmin.equals(currentUsername);

                if (isUserInGroup) {
                    Group group = new Group(new ArrayList<>(), new ArrayList<>(), primaryAdmin, name, description, groupPhoto);

                    ArrayList<User> admins = new ArrayList<>();
                    for (int j = 0; j < adminsJsonArray.length(); j++) {
                        admins.add(new User("", adminsJsonArray.getString(j), "", null, false, "", "", ""));
                    }

                    ArrayList<User> members = new ArrayList<>();
                    for (int j = 0; j < membersJsonArray.length(); j++) {
                        members.add(new User("", membersJsonArray.getString(j), "", null, false, "", "", ""));
                    }

                    group.setAdmins(admins);
                    group.setMembers(members);

                    userGroups.add(group);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userGroups;
    }

    public static void leaveGroup(int groupId) {
        try {
            // Read the JSON file containing groups
            String jsonLines = new String(Files.readAllBytes(Paths.get("Groups.json")));
            JSONArray groups = new JSONArray(jsonLines);

            JSONArray updatedGroups = new JSONArray();

            for (int i = 0; i < groups.length(); i++) {
                JSONObject group = groups.getJSONObject(i);
                int currentGroupId = group.getInt("groupId");

                if (currentGroupId == groupId) {
                    String primaryAdmin = group.getString("primaryAdmin");
                    JSONArray admins = group.getJSONArray("admins");
                    JSONArray members = group.getJSONArray("members");

                    int currentUserId = Functionalities.currentUser.getUserId();

                    // Check if the current user is the primary admin
                    if (Functionalities.currentUser.getUsername().equals(primaryAdmin)) {
                        JOptionPane.showMessageDialog(null, "Primary admins cannot leave the group", "Error", JOptionPane.ERROR_MESSAGE);
                        updatedGroups.put(group); // Add the group back unchanged
                        continue; // Skip to the next group
                    }

                    boolean isMember = false;

                    // Check if the current user is an admin or a member
                    for (int j = 0; j < admins.length(); j++) {
                        if (admins.getInt(j) == currentUserId) {
                            isMember = true;
                            admins.remove(j); // Remove the admin
                            break;
                        }
                    }

                    for (int j = 0; j < members.length(); j++) {
                        if (members.getInt(j) == currentUserId) {
                            isMember = true;
                            members.remove(j); // Remove the member
                            break;
                        }
                    }

                    if (!isMember) {
                        JOptionPane.showMessageDialog(null, "Non-members cannot leave the group", "Error", JOptionPane.ERROR_MESSAGE);
                        updatedGroups.put(group); // Add the group back unchanged
                        continue; // Skip to the next group
                    }

                    // Update the group with modified admins and members
                    group.put("admins", admins);
                    group.put("members", members);
                    updatedGroups.put(group);

                    // Notify the user about success
                    if (admins.toList().contains(currentUserId)) {
                        JOptionPane.showMessageDialog(null, "Successfully left the group as an admin", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Successfully left the group", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }

                } else {
                    // Add all other groups unchanged
                    updatedGroups.put(group);
                }
            }

            // Save the updated groups back to the file
            Files.write(Paths.get("Groups.json"), updatedGroups.toString(2).getBytes());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while leaving the group.");
        }
    }

    public static ArrayList<Group> suggestGroups() {
        ArrayList<Group> allGroups = GroupDatabase.readGroups(); // Read all groups from database
        ArrayList<Group> suggestions = new ArrayList<>();

        try {
            ArrayList<Integer> userGroupIds = new ArrayList<>();

            // Read groups.json and identify groups the user is a part of
            if (Files.exists(Paths.get("groups.json"))) {
                String groupsJson = new String(Files.readAllBytes(Paths.get("groups.json")));
                JSONArray groupsArray = new JSONArray(groupsJson);

                for (int i = 0; i < groupsArray.length(); i++) {
                    JSONObject groupJson = groupsArray.getJSONObject(i);
                    
                    String primaryAdmin = groupJson.getString("primaryAdmin");
                    JSONArray adminsJsonArray = groupJson.getJSONArray("admins");
                    JSONArray membersJsonArray = groupJson.getJSONArray("members");

                    // Check if user is already an admin or a member of the group
                    boolean isUserAdmin = adminsJsonArray.toList().contains(Functionalities.currentUser.getUsername());
                    boolean isUserMember = membersJsonArray.toList().contains(Functionalities.currentUser.getUsername());
                    boolean isUserPrimaryAdmin= primaryAdmin.equals(Functionalities.currentUser.getUsername());

                    if (isUserAdmin || isUserMember || isUserPrimaryAdmin) {
                        userGroupIds.add(groupJson.getInt("groupId"));
                    }
                }
            }

            // Filter out groups where the user is already a member or admin
            for (Group group : allGroups) {
                if (!userGroupIds.contains(group.getGroupId())) {
                    suggestions.add(group);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while fetching suggested groups.");
        }

        return suggestions;
    }

    public static void createGroup(String name, String description, String groupPhoto) {
        Group newGroup = new Group(new ArrayList<>(), new ArrayList<>(), Functionalities.currentUser.getUsername(), name, description, groupPhoto);
        //newGroup.getAdmins().add(Functionalities.currentUser);
        //newGroup.getMembers().add(Functionalities.currentUser);

        groupsArray.add(newGroup);
        saveGroups(groupsArray);

        JOptionPane.showMessageDialog(null, "Group created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

}
