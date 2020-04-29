package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.User;
import com.quetinkee.eshop.model.projection.UserList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

  Slice<User> findAllBy(Pageable pageable);

  List<UserList> findAllBy(Sort sort);

  User findByMail(String mail);
}
