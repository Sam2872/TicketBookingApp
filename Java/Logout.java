import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/Logout")
public class Logout extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie[] ck = request.getCookies();
        for(Cookie c : ck){
            if(c.getName().equals("name")){
                c.setMaxAge(0);
                response.addCookie(c);
            }
        }
        response.getWriter().write("1");

    }
}
