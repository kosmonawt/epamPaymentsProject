package controller.web;

import dto.AccountDTO;
import dto.UserDTO;
import org.apache.log4j.Logger;
import service.AccountService;
import service.CardService;
import service.PaymentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/app/user/payment")
public class PaymentController extends HttpServlet {

    private final AccountService accountService = AccountService.getInstance();
    private final PaymentService paymentService = PaymentService.getInstance();
    private final CardService cardService = CardService.getInstance();
    private final Logger logger = Logger.getLogger(PaymentController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");

        if (userDTO != null && userDTO.getId() != null && userDTO.getEmail() != null) {
            List<AccountDTO> accounts = accountService.getAccountsByUserEmail(userDTO.getEmail());
            req.setAttribute("accounts", accounts);

        }

        req.getRequestDispatcher("/WEB-INF/payment.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");

        if (userDTO != null && userDTO.getEmail() != null) {
            Long accNumberFrom = Long.valueOf(req.getParameter("accNumber"));
            Long accNumberTo = Long.valueOf(req.getParameter("accNumTo"));
            if (paymentService.isAccPresentInDB(accNumberTo)) {
                BigDecimal amount = BigDecimal.valueOf(Float.parseFloat(req.getParameter("amount")));
                if (paymentService.checkIfOperationPossible(accNumberFrom, amount)) {
                    logger.warn("Payment, operation is possible");
                    String recipient = req.getParameter("email");
                    if (!accountService.isAccountBlocked(accNumberFrom) && !accountService.isAccountBlocked(accNumberTo)) {
                        logger.warn("Accounts is not blocked");
                        if (accountService.isCurrenciesEquals(accNumberFrom, accNumberTo)) {
                            logger.warn("Currencies is equals");
                            paymentService.create(accNumberFrom, accNumberTo, amount, userDTO.getEmail(), recipient);
                            resp.sendRedirect(req.getContextPath() + "/app/user");
                        } else {
                            logger.warn("Currencies is not equals");
                            resp.sendError(403, "Currencies is different");
                        }
                    } else {
                        logger.warn("Accounts is blocked");
                        resp.sendError(403, "Operation not allowed for BLOCKED acc");
                    }
                } else {
                    logger.warn("Payment, operation is possible");
                    resp.sendError(403, "You have not enough money in account");
                }
            } else {
                resp.sendError(403, "Account not present in system");
            }
        }

    }
}
