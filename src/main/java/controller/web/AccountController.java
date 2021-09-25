package controller.web;

import controller.RegexPattern;
import dto.AccountDTO;
import dto.UserDTO;
import entity.Currency;
import org.apache.log4j.Logger;
import service.AccountService;
import service.PaymentService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/app/user/accounts")
public class AccountController extends HttpServlet {
    private final Logger logger = Logger.getLogger(AccountController.class);
    private final AccountService accountService = AccountService.getInstance();
    private final PaymentService paymentService = PaymentService.getInstance();
    private final UserService userService = UserService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("get accounts page");
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
        logger.debug("user id from session is " + userDTO.getId());
        req.setAttribute("currencies", Currency.values());
        logger.debug("user id: " + userDTO.getId());

        if (userDTO.getId() != null && !userDTO.isAdmin()) {
            List<AccountDTO> accounts;
            try {
                accounts = accountService.getAccountsByUserEmail(userDTO.getEmail());
                req.setAttribute("accounts", accounts);
                req.getRequestDispatcher("/WEB-INF/accounts.jsp").forward(req, resp);
            } catch (NumberFormatException e) {
                logger.warn("wrong id ");
                logger.debug(e.getMessage());
                e.printStackTrace();
            }
        }
        if (userDTO.getId() != null && userDTO.isAdmin()) {
            String email = String.valueOf(req.getParameter("userAcc"));
            Pattern pattern = Pattern.compile(RegexPattern.EMAIL);
            Matcher matcher = pattern.matcher(email);
            List<AccountDTO> accounts;
            if (matcher.find()) {
                if (userService.existsByEmail(email)) {
                    logger.debug("admin get accounts by email: " + email);
                    accounts = accountService.getAccountsByUserEmail(email);
                    req.setAttribute("accounts", accounts);
                    req.getRequestDispatcher("/WEB-INF/accounts.jsp").forward(req, resp);
                } else {
                    logger.warn("email not present  in system");
                    resp.sendError(404, "User not present in system");
                }
            } else {
                resp.sendError(415, "Email is not correct");
            }

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
        if (req.getSession() != null && req.getSession().getAttribute("user") != null) {
            logger.debug("creating account for user: " + userDTO.getId() + " with currency " + req.getAttribute("currency1"));
            logger.debug("user id: " + userDTO.getId());
            String currency = String.valueOf(req.getParameter("currency"));
            logger.debug("currency is " + currency);
            accountService.createAccountForUser(userDTO.getEmail(), currency);
            logger.debug("redirecting to user accounts");
            resp.sendRedirect("/app/user/accounts");
        } else {
            logger.warn("session or user is null, in POST method in Account Controller ");
        }

    }
}
