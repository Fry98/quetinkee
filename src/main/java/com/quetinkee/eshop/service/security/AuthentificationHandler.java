package com.quetinkee.eshop.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quetinkee.eshop.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

@Service
public class AuthentificationHandler implements LogoutSuccessHandler, AuthenticationFailureHandler, AuthenticationSuccessHandler {

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication a) throws IOException, ServletException {
    this.setResponse(response, HttpStatus.OK.value(), "Logout success");
  }

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException ae) throws IOException, ServletException {
    this.setResponse(response, HttpStatus.FORBIDDEN.value(), ae.getMessage());
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication a) throws IOException, ServletException {
//    this.setResponse(response, HttpStatus.OK.value(), "Login success");
    User user = ((UserDetail) a.getPrincipal()).getUser();
    ObjectMapper objectMapper = new ObjectMapper();
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpStatus.OK.value());
    response.getWriter().write(objectMapper.writeValueAsString(user));
    response.getWriter().flush();
  }

  private void setResponse(HttpServletResponse response, int code, String message) throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(code);
    response.getWriter().write("{\"message\": \"" + message + "\"}");
    response.getWriter().flush();
  }
}