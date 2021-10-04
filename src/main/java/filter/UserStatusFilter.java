package filter;


import dto.UserDTO;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserStatusFilter implements Filter {

    public final String message = "Your account is blocked, please contact with your administrator";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        if (user != null && user.isBlocked()) {
            resp.sendError(403, message);
        } else {
            chain.doFilter(req, resp);
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
