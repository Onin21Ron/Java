import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Controller
public class RegisterController {

    // H2 Database configuration details
    private final String DB_URL = "jdbc:h2:~/mydb;AUTO_SERVER=TRUE";
    private final String DB_USER = "sa";
    private final String DB_PASSWORD = "";

    public RegisterController() {
        // Automatically creates the table when Spring starts up!
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                "username VARCHAR(50) UNIQUE NOT NULL, " +
                                "password VARCHAR(255) NOT NULL);";
        try {
            Class.forName("org.h2.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSQL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // This listens specifically for the POST request coming from your HTML form
    @PostMapping("/register")
    @ResponseBody // This tells Spring to return plain text/HTML instead of looking for a view file
    public String handleRegistration(@RequestParam("username") String username, 
                                     @RequestParam("password") String password) {
        
        String insertSQL = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            
            return "<h3>Success! Account created and saved to H2 Database via Spring Boot.</h3>" +
                   "<p>Welcome, " + username + "!</p>" +
                   "<a href='/index.html'>Back to Form</a>";

        } catch (Exception e) {
            return "<h3>Database Error: " + e.getMessage() + "</h3>" +
                   "<a href='/index.html'>Try Again</a>";
        }
    }
}





// import java.io.IOException;
// import java.io.PrintWriter;
// import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

// @WebServele("/register")
// public class RegiisterServlet extends HttpServlet {

//   @Override
//   protected void doPost(HttpServletRequest request, HttpServletResponse response)
//   throws ServletException, IOException {
//     String username = request.getParameter("username");
//     String password = request.getParameter("password");

//     // String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

//     response.setContentType("text/html");
//     PrintWriter out = response.getWriter();

//     out.printIn("<html><body>");
//     out.printIn("<h3>Registration Successful</h3>");
//     out.printIn("<p><strong>Registration received:</strong> " + username + "</p>");
//     out.printIn("<p><strong>Password received:</strong> " + pasword + "</p>");
//     out.printIn("<a href='index.html'>Go Back</a>");
//     out.printIn("</body></html>");
//   };
// }