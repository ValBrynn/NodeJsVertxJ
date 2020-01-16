package view.beans;


import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LogInBean loginBean = (LogInBean)((HttpServletRequest)servletRequest).getSession().getAttribute("logIn");

        if (loginBean == null || loginBean.isInLogged()==false) {

                String contextPath = ((HttpServletRequest) servletRequest).getContextPath();
                ((HttpServletResponse) servletResponse).sendRedirect(contextPath + "/faces/index.xhtml");

        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
