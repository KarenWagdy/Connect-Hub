/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author nouri
 */
public class GroupEditing {
    
    public ImageIcon changePFP(File f,Group g) {
    ImageIcon profilePicture = new ImageIcon(f.getAbsolutePath());
    Image pfp = profilePicture.getImage();
    Image scaledPFP = pfp.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
    ImageIcon scaledIcon = new ImageIcon(scaledPFP);
    
    ArrayList<Group> pfpChange = GroupDatabase.readGroups();
    g.setGroupPhoto(f.getAbsolutePath());
    for (int i = 0; i < pfpChange.size(); i++) {
        if (pfpChange.get(i).getGroupId() == g.getGroupId()) {
            pfpChange.set(i, g); 
            break; 
        }
    }
    GroupDatabase.saveGroups(pfpChange);
                
    return scaledIcon;
}

    public static ArrayList<Object> search(String name) {
        name = name.toLowerCase();

        ArrayList<User> users = UsersDatabase.readUsers();
        ArrayList<Group> groups = GroupDatabase.readGroups();

        ArrayList<Object> result = new ArrayList<>();

        ArrayList<Integer> pendingGroupIds = new ArrayList<>();
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("membershipRequest.json")));
            JSONArray membershipRequests = new JSONArray(jsonLines);

            for (int i = 0; i < membershipRequests.length(); i++) {
                JSONObject request = membershipRequests.getJSONObject(i);
                int userId = request.getInt("userId");
                int groupId = request.getInt("groupId");
                String status = request.getString("status");

                if (userId == Functionalities.currentUser.getUserId() && "Pending".equalsIgnoreCase(status)) {
                    pendingGroupIds.add(groupId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading membership requests.");
        }

        for (User user : users) {
            if (user.getUsername().toLowerCase().contains(name)
                    && !user.getUsername().equalsIgnoreCase(Functionalities.currentUser.getUsername())) {
                result.add(user);
            }
        }

        for (Group group : groups) {
            if (group.getName().toLowerCase().contains(name)
                    && !pendingGroupIds.contains(group.getGroupId())) {
                result.add(group);
            }
        }

        return result;
    }

    public static int validateGroupName(String name) {
        ArrayList<Group> groups = GroupDatabase.readGroups();
        for (Group g : groups) {
            if (g.getName().equalsIgnoreCase(name)) {
                return 1; 
            }
        }
        return 2; 
    }
   

}
