package controller;

import controller.web.RegistrationController;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;


public class RegistrationControllerTest {
    private final static String registrationPath = "/WEB-INF/registration.jsp";
    private final static String homePAth = "/home";
    private double counter = Math.random();

    final HttpServletRequest request = mock(HttpServletRequest.class);
    final HttpServletResponse response = mock(HttpServletResponse.class);
    final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    final RegistrationController servlet = new RegistrationController();


    @Test
    @DisplayName("get registration page test")
    public void whenCallDoGetThenServletReturnRegistrationPage() throws ServletException, IOException {

        when(request.getRequestDispatcher(registrationPath)).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(registrationPath);
        verify(request, never()).getSession();
        verify(dispatcher).forward(request, response);

    }

    @Test
    @DisplayName("Redirect to homepage after registration")
    public void getRedirectToHomePageWhenRegistrationSuccessful() throws ServletException, IOException {

        when(request.getParameter("email")).thenReturn("email@email.com" + counter++);
        when(request.getParameter("password")).thenReturn("pass" + counter++);
        when(request.getParameter("name")).thenReturn("name" + counter++);
        when(request.getParameter("surname")).thenReturn("surname" + ++counter);


        servlet.doPost(request, response);

        verify(response, times(1)).sendRedirect(homePAth);


    }


}
