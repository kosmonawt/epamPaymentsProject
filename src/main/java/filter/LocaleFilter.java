package filter;


import locale.SupportedLocale;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public class LocaleFilter implements Filter {

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

        if (request.getParameter(LANG) != null){
            replaceUserLocale(request);
        }
        if (request.getSession().getAttribute(LOCALE) == null){
            setUserLocale(request);
        }

        filterChain.doFilter(request, servletResponse);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    public void replaceUserLocale(HttpServletRequest request){
        HttpSession session = request.getSession();
        String language = request.getParameter(LANG);
        Locale locale = SupportedLocale.getDefaultOrLocale(language);
        session.setAttribute(LOCALE, locale);
    }

    public void setUserLocale(HttpServletRequest request){
        HttpSession session = request.getSession();
        Locale locale = SupportedLocale.getDefault();
        session.setAttribute(LOCALE, locale);
    }

}
