package src;

import java.io.*;
import java.util.*;

public class UserManager {
    private List<User> users = new ArrayList<>();
    private static final String DATA_DIRECTORY = "src/user_data"; // Define directory path
    private static final String DATA_FILE = DATA_DIRECTORY + "/user_data.txt"; // Define file path

    public UserManager() {
        loadUserData(); // Load data when the UserManager is initialized
    }

    // Signup method to add new user and save to file
    public User signup(String username, String password, String email) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists. Try a different one.");
                return null;
            }
        }
        User newUser = User.signup(username, password, email);
        users.add(newUser);
        saveUserData(); // Save after adding new user
        return newUser;
    }

    // Login method
    public User login(String username, String password) {
        for (User user : users) {
            if (user.login(username, password)) {
                System.out.println("Login successful. Welcome back, " + username + "!");
                return user;
            }
        }
        System.out.println("Invalid username or password.");
        return null;
    }

    // Password reset with persistence
    public void resetPassword(String username, String email) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getEmail().equals(email)) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                user.setPassword(newPassword);
                saveUserData(); // Save changes
                System.out.println("Password reset successful for " + username + ".");
                return;
            }
        }
        System.out.println("User with provided credentials not found.");
    }

    // Save user data to file
    private void saveUserData() {
        // Ensure the directory exists
        File directory = new File(DATA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it does not exist
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getEmail() + "\n");
                // Add more details as required for transactions, budgets, etc.
            }
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }

    // Load user data from file
    private void loadUserData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return; // No data to load if file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String username = parts[0];
                    String password = parts[1];
                    String email = parts[2];
                    users.add(new User(username, password, email));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading user data: " + e.getMessage());
        }
    }
}
