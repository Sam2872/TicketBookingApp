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


@WebServlet("/Movies")
public class Movies extends HttpServlet {

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
                String check = "select * from movies";
                ResultSet rs = stmt.executeQuery(check);
                JSONArray arr = new JSONArray();


                String movie_name = "", year = "", img_url = "";
                int count = 0, movie_rating = 0, movie_id;

                while (rs.next()) {
                    JSONObject subObj = new JSONObject();
                    count++;
                    movie_id = rs.getInt("movie_id");
                    movie_name = rs.getString("movie_name");
                    movie_rating = rs.getInt("movie_rating");
                    year = rs.getString("year");
                    img_url = rs.getString("img_url");

                    subObj.put("movie_id", movie_id);
                    subObj.put("movie_name", movie_name);
                    subObj.put("movie_rating", movie_rating);
                    subObj.put("year", year);
                    subObj.put("img_url", img_url);
                    arr.add(subObj);
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