package panda.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/faces/jsf/index.xhtml","/faces/jsf/login.xhtml","/faces/jsf/register.xhtml","/"})
public class LoggedInUserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();

        if (session.getAttribute("username") != null){
            response.sendRedirect("/faces/jsf/home.xhtml");
        }else{
            filterChain.doFilter(request,response);
        }


    }
}
