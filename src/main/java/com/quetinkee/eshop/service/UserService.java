package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.AddressDao;
import com.quetinkee.eshop.dao.UserDao;
import com.quetinkee.eshop.model.Address;
import com.quetinkee.eshop.model.User;
import java.util.Objects;
import com.quetinkee.eshop.model.Role;
import com.quetinkee.eshop.model.User_;
import com.quetinkee.eshop.model.projection.UserList;
import com.quetinkee.eshop.utils.ValidationError;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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
    return !password.isEmpty() && password.length() > 5;
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

  @Transactional(readOnly = true)
  public Slice<User> findAll(Integer pageNum, Integer pageSize) {
    Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(User_.LAST_NAME, User_.FIRST_NAME));
    return this.dao.findAllBy(paging);
  }

  @Transactional(readOnly = true)
  public List<UserList> getList() {
    return this.dao.findAllBy(Sort.by(User_.LAST_NAME, User_.FIRST_NAME));
  }

  @Transactional
  public void delete (User user) {
    Objects.requireNonNull(user);
    // TODO: check pending orders first
    this.dao.delete(user);
  }

  /**
   * Create user from registration form
   * @param user
   */
  public void createRegistred(User user) {
    Objects.requireNonNull(user);
    if (user.getAddressDelivery() == null) {
      throw new ValidationError("Zadejte doručovací adresu");
    }
    this.create(user);
  }

  /**
   * Create user in administration
   * @param user
   */
  @Transactional
  public void create(User user) {
    Objects.requireNonNull(user);
    if (!this.checkPassword(user.getPassword())) {
      throw new ValidationError("Zadejte heslo");
    }
    if (this.isRegistred(user.getMail())) {
      throw new ValidationError("Uživatel se zadaným e-mailem je již registrován");
    }
    this.persist(user);
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
  public User update(User original, User user) {
    Objects.requireNonNull(original);
    Objects.requireNonNull(user);

    // password
    if (user.getPassword() != null) {
      if (!this.checkPassword(user.getPassword())) {
        throw new ValidationError("Zadejte heslo");
      }
      original.setPassword(user.getPassword());
      this.encodePassword(original);
    }

    if (user.getFirstName() != null) {
      original.setFirstName(user.getFirstName());
    }
    if (user.getLastName() != null) {
      original.setLastName(user.getLastName());
    }
    if (user.getPhone() != null) {
      original.setPhone(user.getPhone());
    }

    // delivery address update / create
    if (user.getAddressDelivery() != null) {
      if (original.getAddressDelivery() == null) {
        original.setAddressDelivery(user.getAddressDelivery());
      }
      else {
        this.updateAddress(original.getAddressDelivery(), user.getAddressDelivery());
      }
    }

    //billing address update / create / remove
    if (user.getAddressBilling() != null) {
      if (original.getAddressBilling() != null && this.isAddressEmpty(user.getAddressBilling())) {
        original.setAddressBilling(null);
      }
      else if (original.getAddressBilling() == null) {
        original.setAddressBilling(user.getAddressBilling());
      }
      else {
        this.updateAddress(original.getAddressBilling(), user.getAddressBilling());
      }
    }
    this.dao.save(original);

    return original;
  }

  @Transactional
  public void updateAddress (Address original, Address address) {
    Objects.requireNonNull(original);
    Objects.requireNonNull(address);

    if (address.getStreet() != null) {
      original.setStreet(address.getStreet());
    }
    if (address.getCity() != null) {
      original.setCity(address.getCity());
    }
    if (address.getZip() != null) {
      original.setZip(address.getZip());
    }
    this.addressDao.save(original);
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

  private boolean isAddressEmpty (Address address) {
    return address.getStreet() == null && address.getCity() == null && address.getZip() == null;
  }
}
