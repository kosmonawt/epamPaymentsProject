package controller.web;

import dto.PaymentDTO;
import dto.UserDTO;
import org.apache.log4j.Logger;
import service.PaymentService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/app/admin")
public class AdminPageController extends HttpServlet {

    private final Logger logger = Logger.getLogger(AdminPageController.class);
    private final UserService userService = UserService.getInstance();
    private final PaymentService paymentService = PaymentService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
        if (userDTO != null && userDTO.isAdmin()) {
            logger.warn("Admin logged in");
            List<UserDTO> users = userService.getAll();
            req.setAttribute("users", users);
           // List<PaymentDTO> payments = paymentService.getAllPaymentsWithStatusSend();
         //   req.setAttribute("payments", payments);
            req.getRequestDispatcher("/WEB-INF/admin-page.jsp").forward(req, resp);
        } else {
            logger.warn("Try to access to admin page without rights");
            resp.sendError(403, "You have no access to this page");
        }

    }
}
