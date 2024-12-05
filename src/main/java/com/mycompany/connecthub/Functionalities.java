/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connecthub;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Base64;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author karen
 */
public class Functionalities {

    static User currentUser;

    public static String passwordHashing(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);

    }

    public static boolean isValidEmail(String email) {
        InternetAddress emailAddress = null;
        if (email.endsWith(".com")) {

            try {
                emailAddress = new InternetAddress(email);
                emailAddress.validate();
                return true;
            } catch (AddressException e) {
                return false;
            }
        } else {
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

    public static boolean isValidDOB(LocalDate dob) {
        if (dob.isAfter(LocalDate.now())) {
            return false;
        }

        int age = Period.between(dob, LocalDate.now()).getYears();

        return age >= 13;
    }

    public static int signup(String email, String userName, String password, LocalDate dateOfBirth) {

        if (!(isValidEmail(email))) {
            return 1;
            //email is not valid
        }
        ArrayList<User> users = UsersDatabase.readUsers();
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
        if (!isValidDOB(dateOfBirth)) {
            return 5; //invalid date of birth
        }

        User user = new User(email, userName, password, dateOfBirth, true);
        UsersDatabase.usersArray.add(user);
        UsersDatabase.saveUsers(UsersDatabase.usersArray);

        return 6;

    }

    public static int login(String email, String password) {
        ArrayList<User> loginUsers = UsersDatabase.readUsers();
        for (User u : loginUsers) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                try {
                    if (u.getPassword().equals(passwordHashing(password))) {
                        System.out.println("In func "+ passwordHashing(password));
                        u.setStatus("Online");
                        currentUser = u;
                        System.out.println(currentUser.getUsername());
                        UsersDatabase.saveUsers(loginUsers);
                        return 1;  // success login 
                    }
                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return 2; // incorrect pass or email
    }

    public static User getUser(int userId) {
        ArrayList<User> users = UsersDatabase.readUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == userId) {
                return users.get(i);
            }
        }
        return null;
    }

    public static int getUserId(String username) {
        ArrayList<User> users = UsersDatabase.readUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername() == username) {
                return users.get(i).getUserId();
            }
        }
        return 0;
    }
    
    public static User getUser(String username) {
        ArrayList<User> users = UsersDatabase.readUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername() == username) {
                return users.get(i);
            }
        }
        return null;
    }
}
