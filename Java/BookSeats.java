import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.sql.*;


@WebServlet("/BookSeats")
public class BookSeats extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_id = "";
        String show_id = "";
        Cookie[] ck = request.getCookies();
        for(Cookie c : ck){
            if(c.getName().equals("user_id")){
                user_id  = c.getValue();
            }
            if(c.getName().equals("show_id")){
                show_id = c.getValue();
            }
        }
        String  seats = request.getParameter("seats");
        String[] arr = seats.split(",");
        int j = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection( "jdbc:mysql://localhost:3306/bookingsystem","root","123");
            Statement st = con.createStatement();
            PreparedStatement stmt = con.prepareStatement("Insert into seats(user_id, show_id, seat_number) values(?,?,?)");

            for(int i = 0 ;i< arr.length;i++){
                stmt.setInt(1,Integer.parseInt(user_id) );
                stmt.setInt(2,Integer.parseInt(show_id) );
                stmt.setInt(3,Integer.parseInt(arr[i]));
                 j = stmt.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        response.getWriter().write(String.valueOf(j));


    }
}

