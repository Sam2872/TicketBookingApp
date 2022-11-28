import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


@WebServlet("/Theaters")
public class Theaters extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies ){
            if(c.getName().equals("name")) username  = c.getValue();
        }
        if(username!=null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookingsystem", "root", "123");
                Statement stmt = con.createStatement();
                String check = "select * from theater";
                ResultSet rs = stmt.executeQuery(check);
                JSONArray arr = new JSONArray();
                String json = "";
                int count = 0;
                while (rs.next()) {
                    int id = rs.getInt("theater_id");
                    String name = rs.getString("theater_name");
                    String location = rs.getString("theater_location");

                    JSONObject obj = new JSONObject();
                    obj.put("theater_id", id);
                    obj.put("theater_name", name);
                    obj.put("theater_location", location);
                    arr.add(obj);
                    count++;
                }

                if (count != 0) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(arr.toJSONString());
                } else {
                    response.getWriter().write("0");
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            response.getWriter().write("0");
        }
	}


}
