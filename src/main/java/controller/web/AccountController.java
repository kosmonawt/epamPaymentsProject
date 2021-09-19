package controller.web;

import dto.AccountDTO;
import dto.UserDTO;
import entity.Currency;
import org.apache.log4j.Logger;
import service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/app/user/accounts")
public class AccountController extends HttpServlet {
    private final Logger logger = Logger.getLogger(AccountController.class);
    private final AccountService accountService = AccountService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("get accounts page");
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
        logger.debug("user id from session is " + userDTO.getId());
        req.setAttribute("currencies", Currency.values());
        logger.debug("user id: " + userDTO.getId());

        if (userDTO.getId() != null) {
            List<AccountDTO> accounts;
            try {
                accounts = accountService.getAccountsByUserEmail(userDTO.getEmail());
                req.setAttribute("accounts", accounts);
            } catch (NumberFormatException e) {
                logger.warn("wrong id ");
                logger.debug(e.getMessage());
                e.printStackTrace();
            }
            //TODO change in DTO card id to card number
        }


        req.getRequestDispatcher("/WEB-INF/accounts.jsp").forward(req, resp);

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
