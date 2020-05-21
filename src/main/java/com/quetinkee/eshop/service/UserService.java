package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.AddressDao;
import com.quetinkee.eshop.dao.UserDao;
import com.quetinkee.eshop.model.Address;
import com.quetinkee.eshop.model.User;
import java.util.Objects;
import com.quetinkee.eshop.model.enums.Role;
import com.quetinkee.eshop.model.User_;
import com.quetinkee.eshop.model.projection.UserList;
import com.quetinkee.eshop.utils.ValidationException;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService extends GenericAdminService<UserDao, User, UserList> {

  private final AddressDao addressDao;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(Validator validator, UserDao dao, AddressDao addressDao, PasswordEncoder passwordEncoder) {
    super(validator, dao, Sort.by(User_.LAST_NAME, User_.FIRST_NAME));
    this.addressDao = addressDao;
    this.passwordEncoder = passwordEncoder;
  }

  public boolean checkPassword(String password) {
    return !password.isEmpty() && password.length() >= 5;
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

  @Transactional
  @Override
  public void delete (User user) {
    Objects.requireNonNull(user);
    // TODO: check pending orders first
    this.dao.delete(user);
  }

  /**
   * Create user from registration form
   * @param user
   * @return
   */
  public User createRegistred(User user) {
    Objects.requireNonNull(user);
    if (user.getId() != null || user.getRole() != null) {
      throw new ValidationException("Not allowed!");
    }
    // TODO: send mail or something
    return this.create(user);
  }

  @Transactional
  @Override
  public User create(User user) {
    Objects.requireNonNull(user);
    if (!this.checkPassword(user.getPassword())) {
      throw new ValidationException("Zadejte heslo");
    }
    if (this.isRegistred(user.getMail())) {
      throw new ValidationException("Uživatel se zadaným e-mailem je již registrován");
    }

    this.encodePassword(user);
    if (user.getRole() == null) {
      user.setRole(Role.USER);
    }
    return this.dao.save(user);
  }

  @Transactional
  @Override
  public User update(User original, User newData) {
    Objects.requireNonNull(original);
    Objects.requireNonNull(newData);

    // password
    if (newData.getPassword() != null) {
      if (!this.checkPassword(newData.getPassword())) {
        throw new ValidationException("Zadejte heslo");
      }
      original.setPassword(newData.getPassword());
      this.encodePassword(original);
    }

    if (newData.getFirstName() != null) original.setFirstName(newData.getFirstName());
    if (newData.getLastName() != null) original.setLastName(newData.getLastName());
    if (newData.getPhone() != null) original.setPhone(newData.getPhone());

    // delivery address update / create
    if (newData.getAddressDelivery() != null) {
      if (original.getAddressDelivery() == null) {
        original.setAddressDelivery(newData.getAddressDelivery());
      }
      else {
        this.updateAddress(original.getAddressDelivery(), newData.getAddressDelivery());
      }
    }

    //billing address update / create / remove
    if (newData.getAddressBilling() != null) {
      if (original.getAddressBilling() != null && this.isAddressEmpty(newData.getAddressBilling())) {
        original.setAddressBilling(null);
      }
      else if (original.getAddressBilling() == null) {
        original.setAddressBilling(newData.getAddressBilling());
      }
      else {
        this.updateAddress(original.getAddressBilling(), newData.getAddressBilling());
      }
    }

    if (this.validate(original)) this.dao.save(original);
    return null;
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
