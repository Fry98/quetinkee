package com.quetinkee.eshop.service.security;

import com.quetinkee.eshop.model.User;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetail implements UserDetails {

  private final User user;
  private final HashSet<GrantedAuthority> authorities;

  public UserDetail(User user) {
    Objects.requireNonNull(user);
    this.user = user;
    this.authorities = new HashSet<>();
    addUserRole();
  }

  public UserDetail(User user, Collection<GrantedAuthority> authorities) {
    Objects.requireNonNull(user);
    Objects.requireNonNull(authorities);
    this.user = user;
    this.authorities = new HashSet<>();
    addUserRole();
    this.authorities.addAll(authorities);
  }

  private void addUserRole() {
    authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.unmodifiableCollection(authorities);
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getMail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public User getUser() {
    return user;
  }

  public void eraseCredentials() {
    user.erasePassword();
  }
}
