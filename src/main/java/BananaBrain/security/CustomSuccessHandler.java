package BananaBrain.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("student"))) {
            response.sendRedirect("/student/home");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("teacher"))) {
            response.sendRedirect("/teacher/home");
        } else {
            response.sendRedirect("/default");
        }
    }
}
