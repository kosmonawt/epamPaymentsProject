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
            Long accNumberFrom = Long.valueOf(req.getParameter("accNum"));
            Long accNumberTo = Long.valueOf(req.getParameter("accNumTo"));
            BigDecimal amount = BigDecimal.valueOf(Float.parseFloat(req.getParameter("amount")));
            String recipient = req.getParameter("email");
            paymentService.create(accNumberFrom, accNumberTo, amount, userDTO.getEmail(), recipient);

        }


//TODO
    }
}
