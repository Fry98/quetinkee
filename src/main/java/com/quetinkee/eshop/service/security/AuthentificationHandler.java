package com.quetinkee.eshop.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quetinkee.eshop.model.User;
import com.quetinkee.eshop.utils.ErrorMessage;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

@Service
public class AuthentificationHandler implements LogoutSuccessHandler, AuthenticationFailureHandler, AuthenticationSuccessHandler {

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication a) throws IOException, ServletException {
    this.setResponse(response, HttpStatus.OK, new ErrorMessage("Odhlášení úspěšné"));
  }

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException ae) throws IOException, ServletException {
    this.setResponse(response, HttpStatus.FORBIDDEN, new ErrorMessage(ae.getMessage()));
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication a) throws IOException, ServletException {
    User user = ((UserDetail) a.getPrincipal()).getUser();
    this.setResponse(response, HttpStatus.OK, user);
  }

  private void setResponse(HttpServletResponse response, HttpStatus code, Serializable err) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(code.value());
    response.getWriter().write(objectMapper.writeValueAsString(err));
    response.getWriter().flush();
  }
}