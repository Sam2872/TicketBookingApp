import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet("/SignUp")
public class SignUp extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
	    String username = request.getParameter("username");
		String password = request.getParameter("password");
        String res = "0";
		int x = 0;
        PrintWriter out = response.getWriter();
		try {
			Class.forName("com.mysql.jdbc.Driver");  
            Connection con=DriverManager.getConnection( "jdbc:mysql://localhost:3306/bookingsystem","root","123"); 
            Statement stmt = con.createStatement();
			String query = "Select * from users where user_name = '"+username+"'";
			ResultSet rs = stmt.executeQuery(query);
			if(!rs.next()) {
				String check = "Insert into users (user_name, password) values ('" + username + "'" + "," + "'" + password + "')";
				stmt.executeUpdate(check);
				Cookie ck = new Cookie("name", username);
				ck.setMaxAge(-1);
				response.addCookie(ck);
				ResultSet rs2 = stmt.executeQuery("Select user_id from users order by user_id desc limit 1");
				while (rs2.next()){
					x = (rs2.getInt("user_id"));
					res  = Integer.toString(x);
				}
				Cookie ck2  = new Cookie("user_id", ""+x);
				ck2.setMaxAge(-1);
				response.addCookie(ck2);
			}
		} catch (SQLException | ClassNotFoundException e) {
           			e.printStackTrace();
		}

        out.println(res);
   
	}
}
