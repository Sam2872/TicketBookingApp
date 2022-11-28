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
import java.util.HashSet;
import java.util.Iterator;


@WebServlet("/GetTickets")
public class GetTickets extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies ){
            if(c.getName().equals("name")) username  = c.getValue();
        }

        if(username!=null){
            String user_id = request.getParameter("user_id");
            String res = "";
            String movie_name = "";
            String theater_name = "";
            String show_time = "";
            String seats = "";
            JSONArray arr = new JSONArray();
            HashSet<Integer> hs = new HashSet<>();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con=DriverManager.getConnection( "jdbc:mysql://localhost:3306/bookingsystem","root","123");
                Statement stmt = con.createStatement();
                String query = "Select show_id from seats where user_id ="+user_id;
                ResultSet rs = stmt.executeQuery(query);
                while(rs. next()){
                    int show_id = rs.getInt("show_id");
                    hs.add(show_id);
                }
                Iterator<Integer> it = hs.iterator();
                while (it.hasNext()){
                    seats="";
                    int x = it.next();
                    String query2 = "Select movie_name , theater_name, show_time from  showtime join movies on movies.movie_id = showtime.movie_id " +
                            "join theater on theater.theater_id = showtime.theater_id where show_id = " +x;
                    ResultSet rs2 = stmt.executeQuery(query2);

                    JSONObject ob = new JSONObject();
                    while(rs2.next()){
                        movie_name = rs2.getString("movie_name");
                        theater_name = rs2.getString("theater_name");
                        show_time = rs2.getString("show_time");
                    }
                    String seatQuery = "Select seat_number from seats where show_id = "+x+" and user_id = "+ user_id;
                    ResultSet rs3 = stmt.executeQuery(seatQuery);
                    while (rs3.next()){
                        int y = rs3.getInt("seat_number");
                        seats += y+",";
                    }
                    int seatl = seats.length() -1;
                    seats =seats.substring(0,seatl);
                    ob.put("movie_name", movie_name);
                    ob.put("theater_name", theater_name);
                    ob.put("show_time", show_time);
                    ob.put("seats", seats);
                    ob.put("show_id", x);
                    arr.add(ob);
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            arr.writeJSONString(response.getWriter());

        }else {
            response.getWriter().write("0");
        }

    }
}
