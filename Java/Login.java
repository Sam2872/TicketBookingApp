import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


 @WebServlet("/Login")
 public class Login extends HttpServlet {

 	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

 	    String username = request.getParameter("username");
 		String password = request.getParameter("password");
         String res = "0";
 		try {
 			Class.forName("com.mysql.jdbc.Driver");
             Connection con=DriverManager.getConnection( "jdbc:mysql://localhost:3306/bookingsystem","root","123");
             Statement stmt = con.createStatement();
 			String check = "select * from users where user_name ='" + username + "'and password = '"+password+"'";
 			ResultSet rs = stmt.executeQuery(check);
            
        
 			if (rs.next()) {
 				res = rs.getString("user_id");
				Cookie ck = new Cookie("name", username);
				ck.setMaxAge(-1);
				Cookie ck2  = new Cookie("user_id", res);
				ck2.setMaxAge(-1);
				response.addCookie(ck);
				response.addCookie(ck2);
 			}
 		} catch (SQLException | ClassNotFoundException e) {
            			e.printStackTrace();
 		}

         response.getWriter().write(res);
   
 	}
 }
