package controller.web;

import controller.Messages;
import controller.RegexPattern;
import dto.UserDTO;
import locale.SupportedLocale;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/register")
public class RegistrationController extends HttpServlet {
    private final static String SUPPORTED_LANGUAGES = "locales";

    UserService userService = UserService.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(SUPPORTED_LANGUAGES, SupportedLocale.getSupportedLanguages());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        if (email == null || !emailValidator(email)) {
            req.setAttribute("emailValidationError", Messages.EMAIL_ERROR);
            req.getRequestDispatcher("/WEB-INF/registration.jsp").forward(req, resp);
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setName(req.getParameter("name"));
        userDTO.setSurname(req.getParameter("surname"));
        userDTO.setEmail(req.getParameter("email"));
        userDTO.setName(req.getParameter("password"));

        userService.save(userDTO);

        resp.sendRedirect("/");
    }

    private boolean emailValidator(String email) {
        return email.matches(RegexPattern.EMAIL);
    }

    private String simpleRegistrationValidator(HttpServletRequest req) {
        String login = req.getParameter("login");
        if (login == null || login.isBlank()) {
            return "Invalid user login !";
        }
        if (userService.existsByLogin(login)) {
            return "Login is already in use";
        }

        return null;
    }
}
