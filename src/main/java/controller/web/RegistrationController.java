package controller.web;

import controller.Messages;
import controller.RegexPattern;
import dto.UserDTO;
import locale.SupportedLocale;
import service.AccountService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/register")
public class RegistrationController extends HttpServlet {
    private final static String SUPPORTED_LANGUAGES = "locales";

    private final UserService userService = UserService.getInstance();
    private final AccountService accountService = AccountService.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(SUPPORTED_LANGUAGES, SupportedLocale.getSupportedLanguages());
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/registration.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (email == null || !emailValidator(email)) {
            req.setAttribute("emailValidationError", Messages.EMAIL_ERROR);
            req.getRequestDispatcher("/WEB-INF/registration.jsp").forward(req, resp);
        }

        if (email == null || email.isEmpty()) {
            resp.sendError(400, "Email is required");
        } else if (password == null || password.isEmpty()) {
            resp.sendError(400, "Password is required");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setName(req.getParameter("name"));
        userDTO.setSurname(req.getParameter("surname"));
        userDTO.setEmail(req.getParameter("email"));
        userDTO.setPassword(req.getParameter("password"));

        userService.save(userDTO);

        resp.sendRedirect("/home");
    }

    private boolean emailValidator(String email) {
        Pattern pattern = Pattern.compile(RegexPattern.EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private String simpleRegistrationValidator(HttpServletRequest req) {
        String login = req.getParameter("login");
        if (login == null || login.isBlank()) {
            return "Invalid user login !";
        }
        if (userService.existsByEmail(login)) {
            return "Login is already in use";
        }

        return null;
    }
}
