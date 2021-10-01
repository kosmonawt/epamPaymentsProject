package controller.web;

import dto.UserDTO;
import org.apache.log4j.Logger;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
    private final Logger logger = Logger.getLogger(LoginController.class);
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || email.isEmpty()) {
            resp.sendError(400, "Email can't be empty");
        } else if (password == null || password.isEmpty()) {
            resp.sendError(400, "Password can't be empty");
        } else {
            if (userService.find(email, password)) {
                UserDTO userDTO = userService.getUserByEmail(email);
                userDTO.setPassword(null);
                req.getSession().setAttribute("user", userDTO);
                logger.debug("Login successful, redirect to home page");
                resp.sendRedirect("/home");
            } else {
                logger.warn("Incorrect password or login");
                logger.debug("start forwarding to login page");
                resp.sendError(403, "Incorrect password or login");
            }
        }

    }

}

