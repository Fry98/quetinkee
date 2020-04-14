package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

  Slice<User> findAllBy(Pageable pageable);

  User findByMail(String mail);
}
