package controller.web;

import dto.CardDTO;
import dto.UserDTO;
import org.apache.log4j.Logger;
import service.CardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Card page controller
 */
@WebServlet(urlPatterns = "/app/user/accounts/card")
public class CardController extends HttpServlet {

    private final Logger logger = Logger.getLogger(AccountController.class);
    private final CardService cardService = CardService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("get card page");
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
        Long accNumber = Long.valueOf(req.getParameter("accNumber"));

        if (userDTO != null && accNumber != null) {
            List<CardDTO> cards;
            cards = cardService.getCardsByAccountNumber(accNumber);

        }

        super.doGet(req, resp);
    }
}
