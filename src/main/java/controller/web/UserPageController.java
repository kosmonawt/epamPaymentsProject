package controller.web;

import dto.AccountDTO;
import dto.UserDTO;
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


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");

       List<AccountDTO> accounts = accountService.getAccountsByUserId(userDTO.getId());

        req.getRequestDispatcher("/WEB-INF/user-page.jsp").forward(req, resp);

    }
}
