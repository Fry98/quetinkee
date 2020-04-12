package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.Boquet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoquetDao extends JpaRepository<Boquet, Integer> {

}
