package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.UserDao;
import com.quetinkee.eshop.model.User;
import java.util.Objects;
import com.quetinkee.eshop.model.Role;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserDao dao;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserDao dao, PasswordEncoder passwordEncoder) {
    this.dao = dao;
    this.passwordEncoder = passwordEncoder;
  }

  public boolean checkPassword(String password) {
    return !password.isEmpty() && password.length() > 3;
  }

  @Transactional(readOnly = true)
  public boolean isRegistred(String mail) {
    User user = this.dao.findByMail(mail);
    return user != null;
  }

  @Transactional(readOnly = true)
  public User find(Integer id) {
    Optional<User> user = this.dao.findById(id);
    return user.isPresent() ? user.get() : null;
  }

  @Transactional
  public void persist(User user) {
    Objects.requireNonNull(user);
    user.encodePassword(this.passwordEncoder);
    if (user.getRole() == null) {
      user.setRole(Role.USER);
    }
    this.dao.save(user);
  }
}
