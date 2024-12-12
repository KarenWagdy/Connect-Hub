/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import static com.mycompany.connecthub.FriendRequestDatabase.frd;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author karen
 */
public class MembershipRequestDatabase {

    public static MembershipRequestDatabase mrd;

    public MembershipRequestDatabase() {
    }

    public static MembershipRequestDatabase getInstance() {
        if (mrd == null) {
            mrd = new MembershipRequestDatabase();
        }
        return mrd;
    }

    public static void updateMembershipRequest(int userId, int groupId) {
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("membershipRequest.json")));
            JSONArray membershipRequests = new JSONArray(jsonLines);

            for (int i = 0; i < membershipRequests.length(); i++) {
                JSONObject request = membershipRequests.getJSONObject(i);
                if (request.getInt("userId") == userId && request.getInt("groupId") == groupId) {
                    membershipRequests.remove(i);
                    break;
                }
            }

            Files.write(Paths.get("membershipRequest.json"), membershipRequests.toString(2).getBytes()); // Pretty-printed JSON

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveMembershipRequest(int userId, int groupId) {
        JSONArray membershipRequestArray;

        try {
            File file = new File("membershipRequest.json");
            if (file.exists()) {
                String jsonLines = new String(Files.readAllBytes(Paths.get("membershipRequest.json")));
                membershipRequestArray = new JSONArray(jsonLines);
            } else {
                membershipRequestArray = new JSONArray();
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
            return;
        }

        JSONObject newMembershipRequest = new JSONObject();
        newMembershipRequest.put("userId", userId);
        newMembershipRequest.put("groupId", groupId);
        newMembershipRequest.put("status", "Pending");

        membershipRequestArray.put(newMembershipRequest);

        try {
            FileWriter fileWriter = new FileWriter("membershipRequest.json");
            fileWriter.write(membershipRequestArray.toString(2)); 
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    public static ArrayList<Group> readMembershipRequests(int userId) {
        ArrayList<Group> userGroupRequests = new ArrayList<>();

        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("membershipRequest.json")));
            JSONArray membershipRequestJson = new JSONArray(jsonLines);

            for (int i = 0; i < membershipRequestJson.length(); i++) {
                JSONObject request = membershipRequestJson.getJSONObject(i);
                int requestUserId = request.getInt("userId");
                int groupId = request.getInt("groupId");
                String status = request.getString("status");

                if (requestUserId == userId && "Pending".equalsIgnoreCase(status)) {
                    Group group = getGroup(groupId); 
                    if (group != null) {
                        userGroupRequests.add(group);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userGroupRequests;
    }

    public static Group getGroup(int groupId) {
        ArrayList<Group> groups = GroupDatabase.readGroups();
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).getGroupId() == groupId) {
                return groups.get(i);
            }
        }
        return null;
    }

    public static void acceptMembershipRequest(int userId, int groupId) {
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("membershipRequest.json")));
            JSONArray membershipRequests = new JSONArray(jsonLines);

            boolean requestFound = false;

            for (int i = 0; i < membershipRequests.length(); i++) {
                JSONObject request = membershipRequests.getJSONObject(i);
                if (request.getInt("userId") == userId && request.getInt("groupId") == groupId) {
                    membershipRequests.remove(i);
                    requestFound = true;
                    break;
                }
            }

            if (!requestFound) {
                System.out.println("Membership request not found!");
                return;
            }

            try (FileWriter fileWriter = new FileWriter("membershipRequest.json")) {
                fileWriter.write(membershipRequests.toString(2));
            }

            File groupFile = new File("groups.json");
            JSONArray groups = new JSONArray();

            if (groupFile.exists()) {
                String groupJsonLines = new String(Files.readAllBytes(Paths.get("groups.json")));
                groups = new JSONArray(groupJsonLines);
            }

            for (int i = 0; i < groups.length(); i++) {
                JSONObject group = groups.getJSONObject(i);
                if (group.getInt("groupId") == groupId) {
                    JSONArray members = group.getJSONArray("members");
                    User user = Functionalities.getUser(userId); 
                    members.put(user.getUsername());
                    break;
                }
            }

            try (FileWriter fileWriter = new FileWriter("groups.json")) {
                fileWriter.write(groups.toString(2));
            }

            System.out.println("Membership request accepted and user added to the group!");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error processing membership request.");
        }
    }

    public static ArrayList<User> getGroupMembershipRequests(int groupId) {
        ArrayList<User> groupRequests = new ArrayList<>();

        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("membershipRequest.json")));
            JSONArray membershipRequestJson = new JSONArray(jsonLines);

            for (int i = 0; i < membershipRequestJson.length(); i++) {
                JSONObject request = membershipRequestJson.getJSONObject(i);
                int requestGroupId = request.getInt("groupId");
                int userId = request.getInt("userId");
                String status = request.getString("status");

                if (requestGroupId == groupId && "Pending".equalsIgnoreCase(status)) {
                    User user = Functionalities.getUser(userId); 
                    if (user != null) {
                        groupRequests.add(user);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return groupRequests;
    }

    public static void rejectMembershipRequest(int userId, int groupId) {
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("membershipRequest.json")));
            JSONArray membershipRequests = new JSONArray(jsonLines);

            JSONArray updatedRequests = new JSONArray();

            for (int i = 0; i < membershipRequests.length(); i++) {
                JSONObject request = membershipRequests.getJSONObject(i);
                int requestUserId = request.getInt("userId");
                int requestGroupId = request.getInt("groupId");

                if (!(requestUserId == userId && requestGroupId == groupId)) {
                    updatedRequests.put(request);
                }
            }

            Files.write(Paths.get("membershipRequest.json"), updatedRequests.toString(2).getBytes());

            System.out.println("Membership request rejected successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while rejecting membership request.");
        }
    }

    public static void removeMembershipRequestFromFile(int groupId, int userId) {
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("membershipRequest.json")));
            JSONArray membershipRequests = new JSONArray(jsonLines);
            JSONArray updatedRequests = new JSONArray();

            for (int i = 0; i < membershipRequests.length(); i++) {
                JSONObject request = membershipRequests.getJSONObject(i);
                int requestGroupId = request.getInt("groupId");
                int requestUserId = request.getInt("userId");

                if (!(requestGroupId == groupId && requestUserId == userId)) {
                    updatedRequests.put(request);
                }
            }

            Files.write(Paths.get("membershipRequest.json"), updatedRequests.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
