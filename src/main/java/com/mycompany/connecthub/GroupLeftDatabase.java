/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author nouri
 */
public class GroupLeftDatabase {

    public static void saveGroupLeft(int userId, int groupId) {
        JSONArray groupsLeftArray = new JSONArray();

        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("groupsLeft.json")));
            groupsLeftArray = new JSONArray(jsonLines);
        } catch (IOException e) {
        }

        JSONObject groupLeft = new JSONObject();
        groupLeft.put("userId", userId);
        groupLeft.put("groupId", groupId);

        groupsLeftArray.put(groupLeft);

        try {
            FileWriter file = new FileWriter("groupsLeft.json");
            file.write(groupsLeftArray.toString(2)); 
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Integer> readGroupsLeft(int userId) {
        ArrayList<Integer> groupsLeftIds = new ArrayList<>();

        try {
            if (Files.exists(Paths.get("groupsLeft.json"))) {
                String groupsLeftJson = new String(Files.readAllBytes(Paths.get("groupsLeft.json")));
                JSONArray groupsLeftArray = new JSONArray(groupsLeftJson);

                for (int i = 0; i < groupsLeftArray.length(); i++) {
                    JSONObject groupLeftJson = groupsLeftArray.getJSONObject(i);
                    int loggedUserId = groupLeftJson.getInt("userId");
                    int groupId = groupLeftJson.getInt("groupId");

                    if (loggedUserId == userId) {
                        groupsLeftIds.add(groupId);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return groupsLeftIds;
    }
    
    public static void removeGroupLeft(int userId, int groupId) {
    try {
        String jsonLines = new String(Files.readAllBytes(Paths.get("groupsLeft.json")));
        JSONArray groupsLeftArray = new JSONArray(jsonLines);
        JSONArray updatedArray = new JSONArray();

        
        for (int i = 0; i < groupsLeftArray.length(); i++) {
            JSONObject groupLeft = groupsLeftArray.getJSONObject(i);
            if (groupLeft.getInt("userId") != userId || groupLeft.getInt("groupId") != groupId) {
                updatedArray.put(groupLeft); 
            }
        }

        FileWriter file = new FileWriter("groupsLeft.json");
        file.write(updatedArray.toString(2));
        file.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
}


}
