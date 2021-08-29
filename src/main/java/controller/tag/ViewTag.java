package controller.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class ViewTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        HttpServletRequest req = getRequest();
        sendUriToJsp(req);
    }

    private HttpServletRequest getRequest() {
        PageContext pageContext = (PageContext) getJspContext();
        return (HttpServletRequest) pageContext.getRequest();
    }

    private void sendUriToJsp(HttpServletRequest request) throws IOException {
        JspWriter jspWriter = getJspContext().getOut();
        jspWriter.print(request.getRequestURI());
    }
}
