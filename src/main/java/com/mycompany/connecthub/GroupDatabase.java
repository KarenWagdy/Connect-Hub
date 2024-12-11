/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        ArrayList<Group> groupsArray = new ArrayList<>();
        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("groups.json")));
            JSONArray groups = new JSONArray(jsonLines);

            for (int i = 0; i < groups.length(); i++) {
                JSONObject groupJson = groups.getJSONObject(i);
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

                groupsArray.add(new Group(admins, members, name, description, groupPhoto));
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
                groupJson.put("name", group.getName());
                groupJson.put("photo", group.getGroupPhoto());
                groupJson.put("description", group.getDescription());
                groupJson.put("primaryAdmin", group.getPrimaryAdmin().getUsername());

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

}
