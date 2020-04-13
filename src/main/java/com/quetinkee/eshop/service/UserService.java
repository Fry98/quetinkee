package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.AddressDao;
import com.quetinkee.eshop.dao.UserDao;
import com.quetinkee.eshop.model.Address;
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
  private final AddressDao addressDao;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserDao dao, AddressDao addressDao, PasswordEncoder passwordEncoder) {
    this.dao = dao;
    this.addressDao = addressDao;
    this.passwordEncoder = passwordEncoder;
  }

  public boolean checkPassword(String password) {
    return !password.isEmpty() && password.length() > 3;
  }

  public void encodePassword(User user) {
    Objects.requireNonNull(user);
    user.encodePassword(this.passwordEncoder);
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
  public void update(User user) {
    Objects.requireNonNull(user);
    this.dao.save(user);
  }

  @Transactional
  public void delete (User user) {
    Objects.requireNonNull(user);
    this.dao.delete(user);
  }

  @Transactional
  public void persist(User user) {
    Objects.requireNonNull(user);
    this.encodePassword(user);

    if (user.getAddressBilling() != null && user.getAddressBilling().getId() == null) {
      this.addressDao.save(user.getAddressBilling());
    }
    else user.setAddressBilling(null);

    if (user.getAddressDelivery() != null && user.getAddressDelivery().getId() == null) {
      this.addressDao.save(user.getAddressDelivery());
    }
    else user.setAddressDelivery(null);

    if (user.getRole() == null) {
      user.setRole(Role.USER);
    }
    this.dao.save(user);
  }

  @Transactional
  public void updateAddress (Address address) {
    Objects.requireNonNull(address);
    this.addressDao.save(address);
  }

  @Transactional
  public void delete (Address address) {
    Objects.requireNonNull(address);
    this.addressDao.delete(address);
  }

  @Transactional
  public void persistAddress (Address address) {
    Objects.requireNonNull(address);
    this.addressDao.save(address);
  }
}
