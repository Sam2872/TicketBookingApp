import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


@WebServlet("/GetSeats")
public class GetSeats extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection( "jdbc:mysql://localhost:3306/bookingsystem","root","123");
            Statement stmt = con.createStatement();
            String json = "0";
            int show_id = 0, count = 0;

            String movie_id =request.getParameter("movie_id").trim();
            String theater_id =request.getParameter("theater_id").trim();
            String show_time =request.getParameter("showTime").trim();

            String check = "select show_id from showtime where  movie_id = "+movie_id+" and theater_id = "
                    + theater_id+ " and show_time = '"+show_time+"'";
            ResultSet rs = stmt.executeQuery(check);

            while(rs.next()){
                show_id = rs.getInt("show_id");
                count++;
            }
            if (count == 0){
                stmt.executeUpdate("Insert into showtime (movie_id,theater_id,show_time)values ("
                        +Integer.parseInt(movie_id.trim())+","+Integer.parseInt(theater_id.trim())+
                        ",'"+show_time+"') ");
                ResultSet res = stmt.executeQuery("SELECT * FROM showtime order by show_id desc limit 1");
                while(res.next()) show_id = res.getInt("show_id");
            }

            response.getWriter().write(show_id+"");


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


}
