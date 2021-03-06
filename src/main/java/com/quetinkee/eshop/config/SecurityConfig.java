package com.quetinkee.eshop.config;

import com.quetinkee.eshop.service.security.ShopAuthenticationFilter;
import com.quetinkee.eshop.service.security.ShopAuthenticationProvider;
import com.quetinkee.eshop.service.security.AuthentificationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthentificationHandler authentificationHandler;

  @Autowired
  private ShopAuthenticationProvider authenticationProvider;

  @Bean
  ShopAuthenticationFilter customUsernamePasswordAuthenticationFilter() throws Exception {
    ShopAuthenticationFilter filter = new ShopAuthenticationFilter();
    filter.setFilterProcessesUrl("/api/login");
    filter.setUsernameParameter("mail");
    filter.setPasswordParameter("pwd");
    filter.setPostOnly(true);
    filter.setAuthenticationSuccessHandler(authentificationHandler);
    filter.setAuthenticationFailureHandler(authentificationHandler);
    filter.setAuthenticationManager(this.authenticationManagerBean());
    return filter;
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .addFilterBefore(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
      .csrf().disable()
      .authorizeRequests()
      .anyRequest().permitAll()
      .and()
        .httpBasic()  // for testing
      .and()
        .logout()
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .deleteCookies("JSESSIONID")
        .logoutUrl("/api/logout")
        .logoutSuccessHandler(authentificationHandler)
      .and()
        .sessionManagement().maximumSessions(1);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

