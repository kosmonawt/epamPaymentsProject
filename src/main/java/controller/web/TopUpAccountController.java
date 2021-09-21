package controller.web;

import dto.UserDTO;
import org.apache.log4j.Logger;
import service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/app/user/accounts/topUp")
public class TopUpAccountController extends HttpServlet {
    private final AccountService accountService = AccountService.getInstance();
    private final Logger logger = Logger.getLogger(PaymentController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
        Long accountNumber = Long.parseLong(req.getParameter("accNumber"));
        if (accountService.checkIfUserHaveAccount(userDTO.getEmail(), accountNumber)) {
            if (!accountService.checkIfAccountIsBlocked(userDTO.getEmail(), accountNumber)) {
                req.setAttribute("accNum", accountNumber);
                req.getRequestDispatcher("/WEB-INF/account-top-up.jsp").forward(req, resp);
            } else {
                logger.warn("User " + userDTO.getEmail() + " try top up BLOCKED account");
                resp.sendError(403, "Account that you try to top up is BLOCKED");
            }
        } else {
            logger.warn("User " + userDTO.getEmail() + " try top up not own account ");
            resp.sendError(403, "You can't top up not your account");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");

        if (userDTO != null && userDTO.getEmail() != null) {
            Long accountNumber = Long.valueOf(req.getParameter("accNum"));
            BigDecimal amount = BigDecimal.valueOf(Float.parseFloat(req.getParameter("amount")));
            accountService.topUpAccount(userDTO.getEmail(), accountNumber, amount);
            resp.sendRedirect(req.getContextPath() + "/app/user/accounts");
        }
    }
}
