import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/GetUserId")
public class GetUserId extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user_id = "";

        Cookie[] cookies = request.getCookies();
        for (Cookie c : cookies ){
            if(c.getName().equals("user_id")) user_id  = c.getValue();
        }

        response.getWriter().write(user_id);
    }
}
