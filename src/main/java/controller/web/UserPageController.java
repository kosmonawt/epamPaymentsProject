package controller.web;

import dto.PaymentDTO;
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
import java.util.List;

@WebServlet("/app/user")
public class UserPageController extends HttpServlet {
    private final AccountService accountService = AccountService.getInstance();
    private final PaymentService paymentService = PaymentService.getInstance();
    private final CardService cardService = CardService.getInstance();
    private final Logger logger = Logger.getLogger(UserPageController.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
        logger.debug("user id: " + userDTO.getId());

        if (userDTO.getId() != null && userDTO.getEmail() != null) {
            try {
                logger.debug("getting user payments");
                List<PaymentDTO> payments;
                payments = paymentService.getAllPaymentsByUserEmail(userDTO.getEmail());

                req.setAttribute("payments", payments);
            } catch (NumberFormatException e) {
                logger.warn("wrong id ");
                logger.debug(e.getMessage());
                e.printStackTrace();
            }
            req.getRequestDispatcher("/WEB-INF/user-page.jsp").forward(req, resp);
        }
    }
}
