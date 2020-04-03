package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class UserDao extends AbstractDao<User> {

    public UserDao() {
        super(User.class);
    }

    public User findByMail(String mail) {
        try {
            return em.createNamedQuery("User.findByMail", User.class).setParameter("username", mail)
                     .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }
}
