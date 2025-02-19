package com.sandeep.phonebook.config;

import java.io.IOException;

import com.sandeep.phonebook.helper.Color;
import com.sandeep.phonebook.helper.Message;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class customeLoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        if (exception instanceof DisabledException) {

            // user is disabled
            HttpSession session = request.getSession();
            session.setAttribute("message",
                    Message.builder()
                            .content("User is disabled, Email with  varification link is sent on your email id !!")
                            .color(Color.red).build());

            response.sendRedirect("/login?disable=true");
        } else {
            response.sendRedirect("/login?error=true");
            // request.getRequestDispatcher("/login").forward(request, response);

        }

    }

}
