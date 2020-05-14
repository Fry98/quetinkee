package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.User;
import com.quetinkee.eshop.model.projection.OptionList;
import com.quetinkee.eshop.model.projection.UserList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends GenericDao<User, UserList> {

  @Override
  @Query(value = "SELECT u.id AS id, u.mail AS name FROM User u")
  List<OptionList> findAllBy(Sort sort);

  User findByMail(String mail);
}
