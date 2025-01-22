//AuthFailureHandler
package com.example.E_Comm.Config;

import com.example.E_Comm.Util.AppConstant;
import com.example.E_Comm.model.UserDetails;
import com.example.E_Comm.repository.UserRepository;
import com.example.E_Comm.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;



@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String email = request.getParameter("username");
        UserDetails userDetails = userRepository.findByEmail(email);

        String errorMessage;

        if (userDetails != null) {
            if (userDetails.getIsEnabled()) {
                if (userDetails.getAccountNonLocked()) {
                    if (userDetails.getFailedAttempt() < AppConstant.ATTEMPT_TIME) {
                        userService.increaseFailedAttempt(userDetails);
                    } else {
                        userService.userAccountLock(userDetails);
                        exception = new LockedException("Your account is Locked !! Please try after sometimes");
                    }
                } else {
                    if (userService.unlockAccountTimeExpired(userDetails)) {
                        exception = new LockedException("Your account is unlocked !! Please try to login");
                    } else {
                        exception = new LockedException("Your account is Locked !! Please try after sometimes");
                    }
                }
            } else {
                exception = new LockedException("Your account is inactive !! Contact support");
            }
        } else {
            exception = new LockedException("Email & password invalid");
        }

        // Only set the message, not the full exception details
        errorMessage = exception.getMessage();
        System.out.println("Authentication failure: " + errorMessage);

        request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", errorMessage);
        response.sendRedirect("/signin?error=" + errorMessage);
    }
}