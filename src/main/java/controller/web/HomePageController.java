package controller.web;

import locale.SupportedLocale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/")
public class HomePageController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, String> languages = new HashMap<>();
        for (SupportedLocale value : SupportedLocale.values()) {
            languages.put(value.getLocale().getLanguage(), value.getLocale().getCountry());
        }
        req.getSession().setAttribute("languages", languages);

        req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);
    }

}
