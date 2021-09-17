package controller.web;

import dto.UserDTO;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Map<String, String> messages = new HashMap<>();

        if (email == null || email.isEmpty()) {
            messages.put("email", "Enter email");

        } else if (password == null || password.isEmpty()) {
            messages.put("password", "Enter password");

        } else if (messages.isEmpty()) {
            if (userService.find(email, password)) {
                UserDTO userDTO = userService.getUserByEmail(email);
                req.getSession().setAttribute("user", userDTO);
            } else {
                messages.put("wrongPass", "Wrong password");
                req.setAttribute("messages", messages);
                req.getRequestDispatcher("/login").forward(req, resp);
            }

        } else {
            req.setAttribute("messages", messages);
            req.getRequestDispatcher("/login").forward(req, resp);
        }
        resp.sendRedirect("/");
    }
}
