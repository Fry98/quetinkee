package com.quetinkee.eshop.config;

/**
* Just testing for login - will be removed / edited later 
*/

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// password
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
      .defaultSuccessUrl("/login?success=true", true)
      .failureUrl("/login?error=true")
      .and()
      .logout()
      .logoutUrl("/api/logout")
      .logoutSuccessUrl("/logout");
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
