import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


@WebServlet("/DeleteTicket")
public class DeleteTicket extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user_id = "";

        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies ){
            if(c.getName().equals("user_id")) user_id  = c.getValue();
        }

        String show_id = request.getParameter("show_id");
        String res = "0";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection( "jdbc:mysql://localhost:3306/bookingsystem","root","123");
            Statement stmt = con.createStatement();
            String delete = "Delete from seats where show_id = "+ Integer.parseInt(show_id)+" and user_id = "+ Integer.parseInt(user_id);
            int result = stmt.executeUpdate(delete);

            if(result == 1){
                res = "1";
            }

            response.getWriter().write(res);


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }



    }
}
