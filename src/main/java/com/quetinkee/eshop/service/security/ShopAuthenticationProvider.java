package com.quetinkee.eshop.service.security;

import com.quetinkee.eshop.dao.UserDao;
import com.quetinkee.eshop.model.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ShopAuthenticationProvider implements AuthenticationProvider {

  private final UserDao dao;
  private final PasswordEncoder encoder;

  public ShopAuthenticationProvider(UserDao dao, PasswordEncoder encoder) {
    this.dao = dao;
    this.encoder = encoder;
  }

  @Override
  public Authentication authenticate(Authentication a) throws AuthenticationException {
    User current = this.dao.findByMail(a.getPrincipal().toString());
    if (current == null || !this.encoder.matches(a.getCredentials().toString(), current.getPassword())) {
      throw new BadCredentialsException("Zadaný e-mail nebo heslo není správné");
    }
    System.out.println("Auth OK: " + a.getPrincipal().toString() + a.getCredentials().toString());
    UserDetail detail = new UserDetail(current);
    detail.eraseCredentials();

    ShopAuthenticationToken token = new ShopAuthenticationToken(detail, detail.getAuthorities());
    token.setAuthenticated(true);
    return token;
  }

  @Override
  public boolean supports(Class<?> type) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(type)
            || ShopAuthenticationToken.class.isAssignableFrom(type);
  }
}
