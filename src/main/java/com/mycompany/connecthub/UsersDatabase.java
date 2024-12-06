/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import static com.mycompany.connecthub.Functionalities.passwordHashing;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author karen
 */
public class UsersDatabase {

    //singleton design pattern implementation
    private static UsersDatabase usersDatabase = null;

    private UsersDatabase() {
    }

    public static UsersDatabase getInstance() {
        if (usersDatabase == null) {
            usersDatabase = new UsersDatabase();
        }
        return usersDatabase;  // if users JSon File database is null make an object of it else return the same object
    }

    static ArrayList<User> usersArray = new ArrayList<>();

    public static ArrayList<User> readUsers() {
        usersArray.clear();

        try {
            String jsonLines = new String(Files.readAllBytes(Paths.get("users.json")));
            JSONArray users = new JSONArray(jsonLines);

            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                String email = user.getString("email");
                String username = user.getString("username");
                int id = user.getInt("userId");
                LocalDate dateOfBirth = LocalDate.parse(user.getString("dateOfBirth"), formatter);
                String password = user.getString("password");
                String status = user.getString("status");
                String profilePicture = user.getString("profilePicture");
                String coverPicture = user.getString("coverPicture");
                String bio = user.getString("bio");

                usersArray.add(new User(email, username, password, dateOfBirth, false, profilePicture, coverPicture, bio));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersArray;
    }

    public static void saveUsers(ArrayList<User> user) {
        JSONArray usersArray = new JSONArray();
        for (User i : user) {
            JSONObject obj = new JSONObject();
            obj.put("userId", i.getUserId());
            obj.put("email", i.getEmail());
            obj.put("username", i.getUsername());
            obj.put("dateOfBirth", i.getDateOfBirth());
            obj.put("status", i.getStatus());

            obj.put("password", i.getPassword());

            obj.put("profilePicture", i.getProfilePicture());
            obj.put("coverPicture", i.getCoverPicture());
            obj.put("bio", i.getBio());

            usersArray.put(obj);

        }
        try {
            FileWriter file = new FileWriter("users.json");
            file.write(usersArray.toString(4));
            file.close();
        } catch (IOException e) {

        }
    }

}
