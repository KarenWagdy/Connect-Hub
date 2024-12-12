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
            // Read the JSON file
            String jsonLines = new String(Files.readAllBytes(Paths.get("membershipRequest.json")));
            JSONArray membershipRequests = new JSONArray(jsonLines);

            // Find and remove the matching request
            for (int i = 0; i < membershipRequests.length(); i++) {
                JSONObject request = membershipRequests.getJSONObject(i);
                if (request.getInt("userId") == userId && request.getInt("groupId") == groupId) {
                    membershipRequests.remove(i);
                    break;
                }
            }

            // Write the updated JSON array back to the file
            Files.write(Paths.get("membershipRequest.json"), membershipRequests.toString(2).getBytes()); // Pretty-printed JSON

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveMembershipRequest(int userId, int groupId) {
        JSONArray membershipRequestArray;

        // Read existing membership requests or initialize a new array
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

        // Create a new membership request object
        JSONObject newMembershipRequest = new JSONObject();
        newMembershipRequest.put("userId", userId);
        newMembershipRequest.put("groupId", groupId);
        newMembershipRequest.put("status", "Pending");

        // Add the new request to the array
        membershipRequestArray.put(newMembershipRequest);

        // Write the updated array back to the file
        try {
            FileWriter fileWriter = new FileWriter("membershipRequest.json");
            fileWriter.write(membershipRequestArray.toString(2)); // Indented for better readability
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    public static ArrayList<Group> readMembershipRequests(int userId) {
        ArrayList<Group> userGroupRequests = new ArrayList<>();

        try {
            // Read and parse the JSON file
            String jsonLines = new String(Files.readAllBytes(Paths.get("membershipRequest.json")));
            JSONArray membershipRequestJson = new JSONArray(jsonLines);

            for (int i = 0; i < membershipRequestJson.length(); i++) {
                JSONObject request = membershipRequestJson.getJSONObject(i);
                int requestUserId = request.getInt("userId");
                int groupId = request.getInt("groupId");
                String status = request.getString("status");

                // Check if the user is associated with the membership request and the status is "Pending"
                if (requestUserId == userId && "Pending".equalsIgnoreCase(status)) {
                    // Retrieve the group information based on the groupId
                    Group group = getGroup(groupId); // Assuming Functionalities.getGroup(groupId) exists
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
            // Read the membership request JSON file
            String jsonLines = new String(Files.readAllBytes(Paths.get("membershipRequest.json")));
            JSONArray membershipRequests = new JSONArray(jsonLines);

            boolean requestFound = false;

            // Find and remove the matching membership request
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

            // Save the updated membership requests back to the file
            try (FileWriter fileWriter = new FileWriter("membershipRequest.json")) {
                fileWriter.write(membershipRequests.toString(2));
            }

            // Read the group data JSON file
            File groupFile = new File("groups.json");
            JSONArray groups = new JSONArray();

            if (groupFile.exists()) {
                String groupJsonLines = new String(Files.readAllBytes(Paths.get("groups.json")));
                groups = new JSONArray(groupJsonLines);
            }

            // Add the user to the group's member list
            for (int i = 0; i < groups.length(); i++) {
                JSONObject group = groups.getJSONObject(i);
                if (group.getInt("groupId") == groupId) {
                    JSONArray members = group.getJSONArray("members");
                    User user = Functionalities.getUser(userId); // Assuming you have a way to get User by userId
                    members.put(user.getUsername());
                    break;
                }
            }

            // Save the updated group data back to the file
            try (FileWriter fileWriter = new FileWriter("groups.json")) {
                fileWriter.write(groups.toString(2));
            }

            System.out.println("Membership request accepted and user added to the group!");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error processing membership request.");
        }
    }

    public static void rejectMembershipRequest(int userId, int groupId) {
        try {
            // Read the membership request JSON file
            String jsonLines = new String(Files.readAllBytes(Paths.get("membershipRequest.json")));
            JSONArray membershipRequests = new JSONArray(jsonLines);

            // Create a new JSON array for the updated membership requests
            JSONArray updatedRequests = new JSONArray();

            // Iterate through the membership requests
            for (int i = 0; i < membershipRequests.length(); i++) {
                JSONObject request = membershipRequests.getJSONObject(i);
                int requestUserId = request.getInt("userId");
                int requestGroupId = request.getInt("groupId");

                // Exclude the request matching the userId and groupId
                if (!(requestUserId == userId && requestGroupId == groupId)) {
                    updatedRequests.put(request);
                }
            }

            // Write the updated requests back to the file
            Files.write(Paths.get("membershipRequest.json"), updatedRequests.toString(2).getBytes());

            System.out.println("Membership request rejected successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while rejecting membership request.");
        }
    }

}
