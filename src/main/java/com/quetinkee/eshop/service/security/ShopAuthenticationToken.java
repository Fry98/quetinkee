package com.quetinkee.eshop.service.security;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ShopAuthenticationToken extends AbstractAuthenticationToken {
  
  public ShopAuthenticationToken(UserDetails details, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.setDetails(details);
  }

  @Override
  public String getCredentials() {
    return ((UserDetails) super.getDetails()).getPassword();
  }

  @Override
  public UserDetails getPrincipal() {
    return (UserDetails) super.getDetails();
  }
}