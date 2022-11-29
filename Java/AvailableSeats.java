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
import java.util.ArrayList;


@WebServlet("/AvailableSeats")
public class AvailableSeats extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies ){
            if(c.getName().equals("name")) username  = c.getValue();
        }
        if(username!=null) {
            String show_id = request.getParameter("show_id");
            Cookie ck = new Cookie("show_id",show_id);
            ck.setMaxAge(-1);
            response.addCookie(ck);
            String res = "0";
            int x = 1;
            ArrayList<Integer> list = new ArrayList<>();

            JSONArray arr = new JSONArray();

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookingsystem", "root", "123");
                Statement stmt = con.createStatement();
                String check = "Select * from seats where show_id = " + Integer.parseInt(show_id);
                ResultSet rs = stmt.executeQuery(check);


                while (rs.next()) {
                    int seat_no = rs.getInt("seat_number");
                    list.add(seat_no);
                }

                while (x <= 20) {
                    JSONObject ob = new JSONObject();
                    if (list.size() > 0 && list.contains(x)) {
                        ob.put("seatNumber", x);
                        ob.put("isBooked", true);
                    } else {
                        ob.put("seatNumber", x);
                        ob.put("isBooked", false);
                    }
                    arr.add(ob);
                    x++;
                }
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(arr.toJSONString());


            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            response.getWriter().write("0");
        }


    }
}
