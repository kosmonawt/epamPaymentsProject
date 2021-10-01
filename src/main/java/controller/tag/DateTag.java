package controller.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Calendar;

public class DateTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            out.println(Calendar.getInstance().getTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;

    }

}
