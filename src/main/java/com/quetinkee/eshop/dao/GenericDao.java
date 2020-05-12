package com.quetinkee.eshop.dao;

import com.quetinkee.eshop.model.AbstractEntity;
import com.quetinkee.eshop.model.projection.InterfaceList;
import com.quetinkee.eshop.model.projection.OptionList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericDao<T extends AbstractEntity, K extends InterfaceList> extends JpaRepository<T, Integer> {

  Slice<K> findAllBy(Pageable pageable);

  List<OptionList> findAllBy(Sort sort);
}
