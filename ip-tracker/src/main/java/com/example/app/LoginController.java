package com.example.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Controller
public class LoginController {

    // Spring Boot will automatically look inside application.properties and inject this!
    @Autowired
    private DataSource dataSource;

    public LoginController() {
        // We handle the table creation inside an initialization block below instead of the constructor
    }

    @PostMapping("/register")
    @ResponseBody 
    public String handleRegistration(@RequestParam("username") String username, 
                                     @RequestParam("password") String password) {
        
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                "username VARCHAR(50) UNIQUE NOT NULL, " +
                                "password VARCHAR(255) NOT NULL);";

        String insertSQL = "INSERT INTO users (username, password) VALUES (?, ?)";

        // Grab the managed connection directly from Spring's dataSource
        try (Connection conn = dataSource.getConnection()) {
            
            // 1. Ensure table exists
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSQL);
            }

            // 2. Insert user
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.executeUpdate();
            }
            
            return "<h3>Success! Account saved to H2 Database via Spring Boot.</h3>" +
                   "<p>Welcome, " + username + "!</p>" +
                   "<a href='/index.html'>Back to Form</a>";

        } catch (Exception e) {
            return "<h3>Database Error: " + e.getMessage() + "</h3>" +
                   "<a href='/index.html'>Try Again</a>";
        }
    }
}





// package com.example.app; // This line MUST match your folder path!

// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.Statement;

// @Controller
// public class LoginController {

//     private final String DB_URL = "jdbc:h2:~/mydb;AUTO_SERVER=TRUE";
//     private final String DB_USER = "sa";
//     private final String DB_PASSWORD = "";

//     public LoginController() {
//         // Automatically creates a 'users' table in H2 when the app starts up
//         String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
//                                 "id INT AUTO_INCREMENT PRIMARY KEY, " +
//                                 "username VARCHAR(50) UNIQUE NOT NULL, " +
//                                 "password VARCHAR(255) NOT NULL);";
//         try {
//             Class.forName("org.h2.Driver");
//             try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//                  Statement stmt = conn.createStatement()) {
//                 stmt.execute(createTableSQL);
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     // Matches the action="register" from your HTML form
//     @PostMapping("/register")
//     @ResponseBody 
//     public String handleRegistration(@RequestParam("username") String username, 
//                                      @RequestParam("password") String password) {
        
//         String insertSQL = "INSERT INTO users (username, password) VALUES (?, ?)";

//         try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//              PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
//             pstmt.setString(1, username);
//             pstmt.setString(2, password);
//             pstmt.executeUpdate();
            
//             return "<h3>Success! Account saved to H2 Database.</h3>" +
//                    "<p>Welcome, " + username + "!</p>" +
//                    "<a href='/index.html'>Back to Form</a>";

//         } catch (Exception e) {
//             return "<h3>Database Error: " + e.getMessage() + "</h3>" +
//                    "<a href='/index.html'>Try Again</a>";
//         }
//     }
// }



// package com.example.app;

// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import jakarta.servlet.http.HttpServletRequest;

// @RestController
// public class LoginController {

//     @PostMapping("/api/login")
//     public String handleLogin(
//             @RequestParam("username") String user,
//             @RequestParam("password") String pass,
//             HttpServletRequest request) {
        
//         // 1. Capture the client's network IP Address
//         String userIp = request.getRemoteAddr();
        
//         // Handle cloud proxy setups (like GitHub Codespaces or routing headers)
//         String proxyIp = request.getHeader("X-Forwarded-For");
//         if (proxyIp != null && !proxyIp.isEmpty()) {
//             userIp = proxyIp.split(",")[0].trim();
//         }

//         // 2. Print the captured data cleanly to your terminal screen below
//         System.out.println("\n==========================================");
//         System.out.println("⚠️ CRITICAL ALERT: LOGIN ATTEMPT DETECTED!");
//         System.out.println("Username: " + user);
//         System.out.println("Password: " + pass);
//         System.out.println("User IP Address: " + userIp);
//         System.out.println("==========================================\n");

//         // 3. Send a confirmation text back to the browser window screen
//         return "Form securely captured! Go back to your GitHub Codespace terminal to see your username, password, and tracked IP address (" + userIp + ").";
//     }
// }
