package controller.web;

import dto.CardDTO;
import dto.UserDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import service.CardService;

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
        if (userDTO != null && req.getParameter("accNumber") != null) {
            Long accNumber = Long.valueOf(req.getParameter("accNumber"));
            List<CardDTO> cards;
            cards = cardService.getCardsByAccountNumber(accNumber);
            logger.debug("setting cards attribute");
            req.setAttribute("accNumber", accNumber);
            req.setAttribute("cards", cards);
            req.getRequestDispatcher("/WEB-INF/cards.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession() != null && req.getSession().getAttribute("user") != null) {
            Long accNumber = Long.valueOf(req.getParameter("accNumber"));
            cardService.createCardForAccount(accNumber);
            resp.sendRedirect("/app/user/accounts/card?accNumber=" + accNumber);
        }
    }
}
