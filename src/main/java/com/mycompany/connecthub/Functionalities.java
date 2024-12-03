/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.time.LocalDate;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author karen
 */
public class Functionalities {
    static ArrayList<User> usersArray = new ArrayList<>();

    public static ArrayList<User> readUsers() {
       // ArrayList<User> usersArray = new ArrayList<>();
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
                usersArray.add(new User( email, username, password, dateOfBirth));
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
            try {
                obj.put("password", passwordHashing(i.getPassword()));
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }

        }
        try {
            FileWriter file = new FileWriter("users.json");
            file.write(usersArray.toString(4));
            file.close();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public static String passwordHashing(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);

    }

    public static boolean isValidEmail(String email) {
        InternetAddress emailAddress = null;

        try {
            emailAddress = new InternetAddress(email);
            emailAddress.validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
    }

    public static boolean isValidPassword(String pass) {
        if (pass == null || pass.length() < 8) {
            return false;
        }
        boolean hasCapital = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasChar = false;

        String Chars = "@#$%^&+=";

        for (char c : pass.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Chars.contains(String.valueOf(c))) {
                hasChar = true;
            }
        }

        return hasCapital && hasLower && hasDigit && hasChar;

    }
    public static boolean isValidDOB(LocalDate dob)
    {
        if (dob.isAfter(LocalDate.now())) {
                return false;
            }

            int age = Period.between(dob, LocalDate.now()).getYears();

            return age >= 13;
    }

    public static int signup(String email, String userName, String password, LocalDate dateOfBirth) {
        if (!(isValidEmail(email))) {
            return 1; //email is not valid
        }
        ArrayList<User> users = readUsers();
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return 2; // email already exists
            } else if (u.getUsername().equalsIgnoreCase(userName)) {
                return 3; // username already exists
            }

        }
        if (!isValidPassword(password)) {
            return 4; //password is not  valid
        }
        if(!isValidDOB(dateOfBirth))
        {
            return 5; //invalid date of birth
        }
        User user = new User( email,  userName,  password,  dateOfBirth);
        usersArray.add(user);
        saveUsers(usersArray);
        
        return 6;
        

    }

}
