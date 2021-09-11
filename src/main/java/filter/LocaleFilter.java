package filter;


import locale.SupportedLocale;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebFilter(urlPatterns = "/*")
public class LocaleFilter implements Filter {

    private static final Logger log = Logger.getLogger(LocaleFilter.class);

    private final static String LANG = "lang";
    private final static String LOCALE = "locale";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getParameter(LANG) != null) {
            replaceUserLocaleFromRequest(request);

        }
        if (request.getSession().getAttribute(LOCALE) == null) {
            setUserLocale(request);
        }
        filterChain.doFilter(request, servletResponse);
    }

    private void replaceUserLocaleFromRequest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = SupportedLocale.getDefaultOrLocale(request.getParameter(LANG));
        session.setAttribute(LOCALE, locale);
        log.warn("Language changed to "+locale);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    public void setUserLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale locale = SupportedLocale.getDefault();
        session.setAttribute(LOCALE, locale);
    }

}
