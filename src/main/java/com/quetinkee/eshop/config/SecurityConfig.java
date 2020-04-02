package com.quetinkee.eshop.config;

/**
* Just testing for login - will be removed / edited later 
* 
* notes:
//      .accessDeniedHandler(authentificationHandler)\
// response.sendError(response.getStatus(), "todo"); not implemented yet
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// password
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// authentificator
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthentificationHandler authentificationHandler;
  
  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
      .withUser("user").password(passwordEncoder().encode("heslo")).roles("ADMIN");
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .authorizeRequests()
      .antMatchers("/admin/**").hasRole("ADMIN")
      .anyRequest().permitAll()
      .and()
      .formLogin()
      .loginPage("/login")
      .loginProcessingUrl("/api/login")
      .usernameParameter("mail").passwordParameter("pwd")
      .successHandler(authentificationHandler)
      .failureHandler(authentificationHandler)
      .and()
      .logout()
      .logoutUrl("/api/logout")
      .logoutSuccessHandler(authentificationHandler);
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

@Service
class AuthentificationHandler implements LogoutSuccessHandler, AuthenticationFailureHandler, AuthenticationSuccessHandler {

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication a) throws IOException, ServletException {
    this.setResponse(response, HttpStatus.OK.value(), "Logout success");
  }

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException ae) throws IOException, ServletException {
    this.setResponse(response, HttpStatus.FORBIDDEN.value(), "Login failed");
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication a) throws IOException, ServletException {
//    this.setResponse(response, HttpStatus.OK.value(), "Login success");
    String userName = ((UserDetails)a.getPrincipal()).getUsername();
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(HttpStatus.OK.value());
    response.getWriter().write("{\"message\": \"" + "Login success" + "\", \"username\": \"" + userName + "\"}");
    response.getWriter().flush();
  }

  private void setResponse (HttpServletResponse response, int code, String message) throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.setStatus(code);
    response.getWriter().write("{\"message\": \"" + message + "\"}");
    response.getWriter().flush();    
  }
}
