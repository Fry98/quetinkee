package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

  User findByMail(String mail);
}
