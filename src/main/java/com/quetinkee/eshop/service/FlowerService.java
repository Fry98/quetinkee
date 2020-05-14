package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.FlowerDao;
import com.quetinkee.eshop.model.Flower;
import com.quetinkee.eshop.model.Flower_;
import com.quetinkee.eshop.model.projection.FlowerList;
import java.util.Objects;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowerService extends GenericAdminService<FlowerDao, Flower, FlowerList> {

  @Autowired
  public FlowerService(Validator validator, FlowerDao dao) {
    super(validator, dao, Sort.by(Flower_.NAME));
  }

  @Transactional
  @Override
  public Flower update(Flower original, Flower newData) {
    Objects.requireNonNull(original);
    Objects.requireNonNull(newData);

    if (newData.getName() != null) original.setName(newData.getName());
    if (newData.getDescription() != null) original.setDescription(newData.getDescription());
    if (newData.getPrice() != null) original.setPrice(newData.getPrice());

    if (this.validate(original)) return this.dao.save(original);
    return null;
  }
}
