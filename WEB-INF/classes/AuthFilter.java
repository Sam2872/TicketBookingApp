import javax.servlet.*;
import java.io.IOException;

public class AuthFilter implements Filter{

    public void init(FilterConfig arg0) throws ServletException {}

    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {

        String username=req.getParameter("name");
        if(username!=null){
            chain.doFilter(req, resp);//sends request to next resource
        }
        else{
            resp.getWriter().print("0");

        }

    }
    public void destroy() {}

}  