package mk.ukim.finki.wp.lab.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
public class IsCourseChosenFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        Object courseId=request.getSession().getAttribute("courseId");
        String path = request.getServletPath();
        String id = request.getParameter("courseId");

        if (id==null && courseId==null && !path.contains("/courses") && !path.contains("/search")){
            request.getSession().setAttribute("hasError", true);
            response.sendRedirect("/courses");
        }else{
           filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
