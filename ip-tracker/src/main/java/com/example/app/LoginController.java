package com.example.app;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class LoginController {

    @PostMapping("/api/login")
    public String handleLogin(
            @RequestParam("username") String user,
            @RequestParam("password") String pass,
            HttpServletRequest request) {
        
        // 1. Capture the client's network IP Address
        String userIp = request.getRemoteAddr();
        
        // Handle cloud proxy setups (like GitHub Codespaces or routing headers)
        String proxyIp = request.getHeader("X-Forwarded-For");
        if (proxyIp != null && !proxyIp.isEmpty()) {
            userIp = proxyIp.split(",")[0].trim();
        }

        // 2. Print the captured data cleanly to your terminal screen below
        System.out.println("\n==========================================");
        System.out.println("⚠️ CRITICAL ALERT: LOGIN ATTEMPT DETECTED!");
        System.out.println("Username: " + user);
        System.out.println("Password: " + pass);
        System.out.println("User IP Address: " + userIp);
        System.out.println("==========================================\n");

        // 3. Send a confirmation text back to the browser window screen
        return "Form securely captured! Go back to your GitHub Codespace terminal to see your username, password, and tracked IP address (" + userIp + ").";
    }
}
