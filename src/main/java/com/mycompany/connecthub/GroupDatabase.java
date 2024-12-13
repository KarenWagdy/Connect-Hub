/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public static ArrayList<User> readGroupAdmins(int groupId) {
        ArrayList<User> admins = new ArrayList<>();

        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("groups.json")));
            JSONArray groupsArray = new JSONArray(jsonLines);

            for (int i = 0; i < groupsArray.length(); i++) {
                JSONObject groupJson = groupsArray.getJSONObject(i);

                if (groupJson.getInt("groupId") == groupId) {
                    JSONArray adminsJsonArray = groupJson.getJSONArray("admins");

                    for (int j = 0; j < adminsJsonArray.length(); j++) {
                        String adminUsername = adminsJsonArray.getString(j);
                        User admin = Functionalities.getUser(adminUsername);
                        admins.add(admin);
                    }

                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading group admins.");
        }

        return admins;
    }

    public static ArrayList<User> readGroupMembers(int groupId) {
        ArrayList<User> members = new ArrayList<>();

        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("groups.json")));
            JSONArray groupsArray = new JSONArray(jsonLines);

            for (int i = 0; i < groupsArray.length(); i++) {
                JSONObject groupJson = groupsArray.getJSONObject(i);

                if (groupJson.getInt("groupId") == groupId) {
                    JSONArray membersJsonArray = groupJson.getJSONArray("members");

                    for (int j = 0; j < membersJsonArray.length(); j++) {
                        String memberUsername = membersJsonArray.getString(j);
                        User member = Functionalities.getUser(memberUsername);
                        members.add(member);
                    }

                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading group members.");
        }

        return members;
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
            String currentUsername = Functionalities.getUser(userId).getUsername();
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
        int groupLeft = 0;
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("groups.json")));
            JSONArray groupsArray = new JSONArray(jsonLines);
            JSONArray updatedGroupsArray = new JSONArray();

            boolean userRemoved = false;
            boolean isPrimary = false;

            for (int i = 0; i < groupsArray.length(); i++) {
                JSONObject groupJson = groupsArray.getJSONObject(i);
                int currentGroupId = groupJson.getInt("groupId");

                if (currentGroupId == groupId) {
                    User currentUser = Functionalities.currentUser;
                    groupLeft = groupId;
                    JSONArray adminsJsonArray = groupJson.getJSONArray("admins");
                    JSONArray updatedAdmins = new JSONArray();
                    for (int j = 0; j < adminsJsonArray.length(); j++) {
                        if (!adminsJsonArray.getString(j).equals(currentUser.getUsername())) {
                            updatedAdmins.put(adminsJsonArray.getString(j));
                        } else {
                            userRemoved = true;
                        }
                    }
                    groupJson.put("admins", updatedAdmins);

                    JSONArray membersJsonArray = groupJson.getJSONArray("members");
                    JSONArray updatedMembers = new JSONArray();
                    for (int j = 0; j < membersJsonArray.length(); j++) {
                        if (!membersJsonArray.getString(j).equals(currentUser.getUsername())) {
                            updatedMembers.put(membersJsonArray.getString(j));
                        } else {
                            userRemoved = true;
                        }
                    }
                    groupJson.put("members", updatedMembers);

                    String primaryAdmin = groupJson.getString("primaryAdmin");
                    if (primaryAdmin.equals(currentUser.getUsername())) {
                        isPrimary = true;
                        JOptionPane.showMessageDialog(null, "Primary admins can not leave the group.", "Error", JOptionPane.INFORMATION_MESSAGE);

                    }
                }

                updatedGroupsArray.put(groupJson);

            }

            Files.write(Paths.get("groups.json"), updatedGroupsArray.toString(4).getBytes());

            if (userRemoved && !isPrimary) {
                JOptionPane.showMessageDialog(null, "You have successfully left the group.", "Error", JOptionPane.INFORMATION_MESSAGE);
                GroupLeftDatabase.saveGroupLeft(Functionalities.currentUser.getUserId(), groupLeft);

            } else if (!userRemoved && !isPrimary) {
                JOptionPane.showMessageDialog(null, "You are not a member of this group.", "Error", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Group> suggestGroups() {
        ArrayList<Group> allGroups = GroupDatabase.readGroups();
        ArrayList<Group> suggestions = new ArrayList<>();

        try {
            ArrayList<Integer> userGroupIds = new ArrayList<>();
            ArrayList<Integer> pendingGroupIds = new ArrayList<>();
            ArrayList<Integer> groupsLeftIds = new ArrayList<>();

            if (Files.exists(Paths.get("groups.json"))) {
                String groupsJson = new String(Files.readAllBytes(Paths.get("groups.json")));
                JSONArray groupsArray = new JSONArray(groupsJson);

                for (int i = 0; i < groupsArray.length(); i++) {
                    JSONObject groupJson = groupsArray.getJSONObject(i);

                    String primaryAdmin = groupJson.getString("primaryAdmin");
                    JSONArray adminsJsonArray = groupJson.getJSONArray("admins");
                    JSONArray membersJsonArray = groupJson.getJSONArray("members");

                    boolean isUserAdmin = adminsJsonArray.toList().contains(Functionalities.currentUser.getUsername());
                    boolean isUserMember = membersJsonArray.toList().contains(Functionalities.currentUser.getUsername());
                    boolean isUserPrimaryAdmin = primaryAdmin.equals(Functionalities.currentUser.getUsername());

                    if (isUserAdmin || isUserMember || isUserPrimaryAdmin) {
                        userGroupIds.add(groupJson.getInt("groupId"));
                    }
                }
            }

            if (Files.exists(Paths.get("membershipRequest.json"))) {
                String requestsJson = new String(Files.readAllBytes(Paths.get("membershipRequest.json")));
                JSONArray requestsArray = new JSONArray(requestsJson);

                for (int i = 0; i < requestsArray.length(); i++) {
                    JSONObject requestJson = requestsArray.getJSONObject(i);
                    int userId = requestJson.getInt("userId");
                    int groupId = requestJson.getInt("groupId");
                    String status = requestJson.getString("status");

                    if (userId == Functionalities.currentUser.getUserId() && "Pending".equalsIgnoreCase(status)) {
                        pendingGroupIds.add(groupId);
                    }
                }
            }

            if (Files.exists(Paths.get("groupsLeft.json"))) {
                String groupsLeftJson = new String(Files.readAllBytes(Paths.get("groupsLeft.json")));
                JSONArray groupsLeftArray = new JSONArray(groupsLeftJson);

                for (int i = 0; i < groupsLeftArray.length(); i++) {
                    JSONObject groupLeftJson = groupsLeftArray.getJSONObject(i);
                    int userId = groupLeftJson.getInt("userId");
                    int groupId = groupLeftJson.getInt("groupId");

                    if (userId == Functionalities.currentUser.getUserId()) {
                        groupsLeftIds.add(groupId);
                    }
                }
            }

            for (Group group : allGroups) {
                if (!userGroupIds.contains(group.getGroupId())
                        && !pendingGroupIds.contains(group.getGroupId())
                        && !groupsLeftIds.contains(group.getGroupId())) {
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
        groupsArray.add(newGroup);
        saveGroups(groupsArray);

        JOptionPane.showMessageDialog(null, "Group created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void removeMember(int groupId, String memberUsername) {
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("groups.json")));
            JSONArray groupsArray = new JSONArray(jsonLines);
            JSONArray updatedGroupsArray = new JSONArray();

            boolean memberRemoved = false;

            for (int i = 0; i < groupsArray.length(); i++) {
                JSONObject groupJson = groupsArray.getJSONObject(i);
                int currentGroupId = groupJson.getInt("groupId");

                if (currentGroupId == groupId) {
                    JSONArray membersJsonArray = groupJson.getJSONArray("members");
                    JSONArray updatedMembers = new JSONArray();

                    for (int j = 0; j < membersJsonArray.length(); j++) {
                        if (!membersJsonArray.getString(j).equals(memberUsername)) {
                            updatedMembers.put(membersJsonArray.getString(j));
                        } else {
                            memberRemoved = true;
                            GroupLeftDatabase.saveGroupLeft(groupId, Functionalities.getUserId(memberUsername)); 
                        }
                    }

                    groupJson.put("members", updatedMembers);
                }

                updatedGroupsArray.put(groupJson);
            }

            Files.write(Paths.get("groups.json"), updatedGroupsArray.toString(4).getBytes());

            if (memberRemoved) {
                JOptionPane.showMessageDialog(null, "The member has been successfully removed from the group.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "The specified user is not a member of this group.", "Error", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while removing the member.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void removeAdmin(int groupId, String adminUsername) {
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("groups.json")));
            JSONArray groupsArray = new JSONArray(jsonLines);
            JSONArray updatedGroupsArray = new JSONArray();

            boolean adminRemoved = false;

            for (int i = 0; i < groupsArray.length(); i++) {
                JSONObject groupJson = groupsArray.getJSONObject(i);
                int currentGroupId = groupJson.getInt("groupId");

                if (currentGroupId == groupId) {
                    String primaryAdminUsername = groupJson.getString("primaryAdmin");

                    if (primaryAdminUsername.equals(adminUsername)) {
                        JOptionPane.showMessageDialog(null, "Primary admin cannot be removed.", "Error", JOptionPane.INFORMATION_MESSAGE);
                        updatedGroupsArray.put(groupJson);
                        continue;
                    }

                    JSONArray adminsJsonArray = groupJson.getJSONArray("admins");
                    JSONArray updatedAdmins = new JSONArray();

                    for (int j = 0; j < adminsJsonArray.length(); j++) {
                        if (!adminsJsonArray.getString(j).equals(adminUsername)) {
                            updatedAdmins.put(adminsJsonArray.getString(j));
                        } else {
                            adminRemoved = true;
                            
                            int userId = Functionalities.getUserId(adminUsername);
                        System.out.println("Removing admin with User ID: " + userId + " from Group ID: " + groupId);


                            GroupLeftDatabase.saveGroupLeft(Functionalities.getUserId(adminUsername), groupId); 
                        }
                    }

                    groupJson.put("admins", updatedAdmins);
                }

                updatedGroupsArray.put(groupJson);
            }

            Files.write(Paths.get("groups.json"), updatedGroupsArray.toString(4).getBytes());

            if (adminRemoved) {
                JOptionPane.showMessageDialog(null, "The admin has been successfully removed from the group.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "The specified user is not an admin of this group.", "Error", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while removing the admin.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void promoteToAdmin(int groupId, String memberUsername) {
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("groups.json")));
            JSONArray groupsArray = new JSONArray(jsonLines);
            JSONArray updatedGroupsArray = new JSONArray();

            boolean memberPromoted = false;

            for (int i = 0; i < groupsArray.length(); i++) {
                JSONObject groupJson = groupsArray.getJSONObject(i);
                int currentGroupId = groupJson.getInt("groupId");

                if (currentGroupId == groupId) {
                    JSONArray membersJsonArray = groupJson.getJSONArray("members");
                    JSONArray adminsJsonArray = groupJson.getJSONArray("admins");
                    JSONArray updatedMembers = new JSONArray();
                    JSONArray updatedAdmins = new JSONArray();

                    for (int j = 0; j < membersJsonArray.length(); j++) {
                        if (!membersJsonArray.getString(j).equals(memberUsername)) {
                            updatedMembers.put(membersJsonArray.getString(j));
                        } else {
                            memberPromoted = true;
                            updatedAdmins = new JSONArray(adminsJsonArray.toString());
                            updatedAdmins.put(memberUsername); 
                        }
                    }

                    groupJson.put("members", updatedMembers);
                    groupJson.put("admins", updatedAdmins);
                }

                updatedGroupsArray.put(groupJson);
            }

            Files.write(Paths.get("groups.json"), updatedGroupsArray.toString(4).getBytes());

            if (memberPromoted) {
                JOptionPane.showMessageDialog(null, "The member has been successfully promoted to admin.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "The specified user is not a member of this group.", "Error", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while promoting the member to admin.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void demoteAdmin(int groupId, String adminUsername) {
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("groups.json")));
            JSONArray groupsArray = new JSONArray(jsonLines);
            JSONArray updatedGroupsArray = new JSONArray();

            boolean adminDemoted = false;

            for (int i = 0; i < groupsArray.length(); i++) {
                JSONObject groupJson = groupsArray.getJSONObject(i);
                int currentGroupId = groupJson.getInt("groupId");

                if (currentGroupId == groupId) {
                    String primaryAdmin = groupJson.getString("primaryAdmin");

                    if (primaryAdmin.equals(adminUsername)) {
                        JOptionPane.showMessageDialog(null, "Primary admin cannot be demoted.", "Error", JOptionPane.INFORMATION_MESSAGE);
                        updatedGroupsArray.put(groupJson);
                        continue;
                    }

                    JSONArray adminsJsonArray = groupJson.getJSONArray("admins");
                    JSONArray membersJsonArray = groupJson.getJSONArray("members");
                    JSONArray updatedAdmins = new JSONArray();
                    JSONArray updatedMembers = new JSONArray(membersJsonArray.toString());

                    for (int j = 0; j < adminsJsonArray.length(); j++) {
                        if (!adminsJsonArray.getString(j).equals(adminUsername)) {
                            updatedAdmins.put(adminsJsonArray.getString(j));
                        } else {
                            adminDemoted = true;
                            updatedMembers.put(adminUsername); 
                        }
                    }

                    groupJson.put("admins", updatedAdmins);
                    groupJson.put("members", updatedMembers);
                }

                updatedGroupsArray.put(groupJson);
            }

            Files.write(Paths.get("groups.json"), updatedGroupsArray.toString(4).getBytes());

            if (adminDemoted) {
                JOptionPane.showMessageDialog(null, "The admin has been successfully demoted to a member.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "The specified user is not an admin of this group.", "Error", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while demoting the admin.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void deleteGroup(int groupId) {
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("groups.json")));
            JSONArray groupsArray = new JSONArray(jsonLines);
            JSONArray updatedGroupsArray = new JSONArray();

            boolean groupDeleted = false;

            for (int i = 0; i < groupsArray.length(); i++) {
                JSONObject groupJson = groupsArray.getJSONObject(i);
                int currentGroupId = groupJson.getInt("groupId");

                if (currentGroupId == groupId) {
                    groupDeleted = true; 
                } else {
                    updatedGroupsArray.put(groupJson);
                }
            }

            Files.write(Paths.get("groups.json"), updatedGroupsArray.toString(4).getBytes());

            if (groupDeleted) {
                deleteGroupPosts(groupId);
                deleteGroupLeftEntries(groupId);
                deleteMembershipRequests(groupId);

                JOptionPane.showMessageDialog(null, "The group and all related data have been successfully deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "The specified group does not exist.", "Error", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while deleting the group.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void deleteGroupPosts(int groupId) {
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("groupPosts.json")));
            JSONArray postsArray = new JSONArray(jsonLines);
            JSONArray updatedPostsArray = new JSONArray();

            for (int i = 0; i < postsArray.length(); i++) {
                JSONObject post = postsArray.getJSONObject(i);
                if (post.getInt("groupId") != groupId) {
                    updatedPostsArray.put(post);
                }
            }

            Files.write(Paths.get("groupPosts.json"), updatedPostsArray.toString(4).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteGroupLeftEntries(int groupId) {
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("groupsLeft.json")));
            JSONArray leftArray = new JSONArray(jsonLines);
            JSONArray updatedLeftArray = new JSONArray();

            for (int i = 0; i < leftArray.length(); i++) {
                JSONObject leftEntry = leftArray.getJSONObject(i);
                if (leftEntry.getInt("groupId") != groupId) {
                    updatedLeftArray.put(leftEntry);
                }
            }

            Files.write(Paths.get("groupsLeft.json"), updatedLeftArray.toString(4).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteMembershipRequests(int groupId) {
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("membershipRequest.json")));
            JSONArray requestsArray = new JSONArray(jsonLines);
            JSONArray updatedRequestsArray = new JSONArray();

            for (int i = 0; i < requestsArray.length(); i++) {
                JSONObject request = requestsArray.getJSONObject(i);
                if (request.getInt("groupId") != groupId) {
                    updatedRequestsArray.put(request);
                }
            }

            Files.write(Paths.get("membershipRequest.json"), updatedRequestsArray.toString(4).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Group getGroup(int groupId) {
        ArrayList<Group> groups = DatabaseFacade.readGroups();
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getGroupId() == groupId) {
                return groups.get(i);
            }
        }
        return null;
    }

}
