// package src;
// public class User {
//     private String username;
//     private String password;
//     private String email;
//     private String profileInfo;

//     public User(String username, String password, String email) {
//         this.username = username;
//         this.password = password;
//         this.email = email;
//         this.profileInfo = "";
//     }

//     public String getUsername() { return username; }
//     public String getEmail() { return email; }
    
//     public void setPassword(String password) { this.password = password; } // Added method to change password

//     public boolean login(String username, String password) {
//         return this.username.equals(username) && this.password.equals(password);
//     }

//     public void updateProfile(String profileInfo) {
//         this.profileInfo = profileInfo;
//     }

//     public static User signup(String username, String password, String email) {
//         System.out.println("Signup successful. Welcome, " + username + "!");
//         return new User(username, password, email);
//     }
// }

package src;

public class User {
    private String username;
    private String password;
    private String email;
    private String profileInfo;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.profileInfo = "";
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    
    public void setPassword(String password) { this.password = password; } 

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void updateProfile(String profileInfo) {
        this.profileInfo = profileInfo;
    }

public String getPassword() {
    return this.password;
}

    public static User signup(String username, String password, String email) {
        System.out.println("Signup successful. Welcome, " + username + "!");
        return new User(username, password, email);
    }
}
