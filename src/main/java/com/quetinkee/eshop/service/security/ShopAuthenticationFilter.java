package com.quetinkee.eshop.service.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class ShopAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private boolean json = false;
  private String userName;
  private String password;

  @Override
  protected String obtainPassword(HttpServletRequest request) {
    if (this.json == true) {
      return this.password;
    }
    return super.obtainPassword(request);
  }

  @Override
  protected String obtainUsername(HttpServletRequest request) {
    if (this.json == true) {
      return this.userName;
    }
    return super.obtainUsername(request);
  }

  /**
   * Read JSON from request
   * @param request
   * @param response
   * @return
   * @throws AuthenticationException 
   */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    if (request.getContentType().contains("json")) {
      try {
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = request.getReader().readLine()) != null) {
          sb.append(line);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(sb.toString());

        this.password = node.findValue(this.getPasswordParameter()).asText();
        this.userName = node.findValue(this.getUsernameParameter()).asText();
        this.json = true;
      }
      catch (IOException ex) {
        Logger.getLogger(ShopAuthenticationFilter.class.getName()).log(Level.INFO, null, ex);
      }
    }
    return super.attemptAuthentication(request, response);
  }
}
