package com.quetinkee.eshop.service;

import com.quetinkee.eshop.dao.BouquetDao;
import com.quetinkee.eshop.dao.OrderDao;
import com.quetinkee.eshop.dao.UserDao;
import com.quetinkee.eshop.model.*;
import com.quetinkee.eshop.model.enums.OrderStatus;
import com.quetinkee.eshop.model.projection.OrderList;
import com.quetinkee.eshop.utils.Storage;
import com.quetinkee.eshop.utils.ValidationException;
import com.quetinkee.eshop.utils.helpers.OrderEdit;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@Service
public class OrderService extends GenericAdminService<OrderDao, Order, OrderList>{

    private final BouquetDao bouquetDao;
    private final UserDao userDao;

    private final Storage storage;

    @Autowired
    public OrderService(Validator validator, OrderDao dao, BouquetDao bouquetDao, UserDao userDao, Storage storage) {
        super(validator, dao, Sort.by(Order_.ID));
        this.bouquetDao = bouquetDao;
        this.userDao = userDao;

        this.storage = storage;
    }

    @Transactional(readOnly = true)
    public Slice<OrderList> getSlice(OrderStatus status, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size, this.sort);
        return this.dao.findAllByStatus(status, paging);
    }

    @Transactional(readOnly = true)
    public Slice<OrderList> getSlice(Integer id, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size, this.sort);
        return this.dao.findAllByUserId(id, paging);
    }

    @Transactional
    public Order changeStatus(Order original, OrderStatus newStatus) {
        Objects.requireNonNull(original);

        if (newStatus != original.getStatus()) {
            switch (original.getStatus()) {
                case NEW:
                    if (newStatus == OrderStatus.STORNO) {
                        this.storage.freeFlowers(original.getContains());
                        original.setStatus(newStatus);
                        break;
                    }
                    if (newStatus == OrderStatus.READY) {
                        this.storage.consumeFlowers(original.getContains());
                        original.setStatus(newStatus);
                        break;
                    }
                case READY:
                    if (newStatus == OrderStatus.STORNO) {
                        original.setStatus(newStatus);
                        break;
                    }
                    if (newStatus == OrderStatus.PENDING) {
                        original.setStatus(newStatus);
                        break;
                    }
                case PENDING:
                    if (newStatus == OrderStatus.STORNO) {
                        original.setStatus(newStatus);
                        break;
                    }
                    if (newStatus == OrderStatus.FINISH) {
                        original.setStatus(newStatus);
                        break;
                    }
                default:
                    throw new ValidationException("Nelze změnit na tento stav");
            }
        }
        // TODO invetory
        return this.dao.save(original);
    }

    @Transactional
    @Override
    public Order update(Order original, Order newData) {
        Objects.requireNonNull(original);
        Objects.requireNonNull(newData);

        if (newData.getUserFirstName() != null) original.setUserFirstName(newData.getUserFirstName());
        if (newData.getUserLastName() != null) original.setUserLastName(newData.getUserLastName());
        if (newData.getUserMail() != null) original.setUserMail(newData.getUserMail());
        if (newData.getUserPhone() != null) original.setUserPhone(newData.getUserPhone());
        if (newData.getPayment() != null) original.setPayment(newData.getPayment());

        // delivery address update / create
        if (newData.getUserAddressDelivery() != null) {
            if (original.getUserAddressDelivery() == null) {
                original.setUserAddressDelivery(newData.getUserAddressDelivery());
            }
            else {
                this.updateAddress(original.getUserAddressDelivery(), newData.getUserAddressDelivery());
            }
        }

        //billing address update / create / remove
        if (newData.getUserAddressBilling() != null) {
            if (original.getUserAddressBilling() != null && this.isAddressEmpty(newData.getUserAddressBilling())) {
                original.setUserAddressBilling(null);
            }
            else if (original.getUserAddressBilling() == null) {
                original.setUserAddressBilling(newData.getUserAddressBilling());
            }
            else {
                this.updateAddress(original.getUserAddressBilling(), newData.getUserAddressBilling());
            }
        }

        if (this.validate(original)) return this.dao.save(original);
        return null;
    }

    @Transactional
    public Order update(Order original, OrderEdit newData) {
        Objects.requireNonNull(original);
        Objects.requireNonNull(newData);

        this.updateItemCounts(original, newData.getKeyItemCount());

        return this.update(original, newData.getOrder());
    }

    public Order createShop(OrderEdit newData, User user) {
        Objects.requireNonNull(newData);

        if (!Objects.equals(newData.getUserId(), user == null ? null : user.getId()) || newData.getOrder().getStatus() != null) {
            throw new ValidationException("Not allowed!");
        }
        return this.create(newData);
    }

    @Transactional
    public Order create(OrderEdit newData) {
        Objects.requireNonNull(newData);
        Order order = newData.getOrder();
        this.updateItemCounts(order, newData.getKeyItemCount());
        if (order.getUser() == null && newData.getUserId() != null) {
            Optional<User> exist = this.userDao.findById(newData.getUserId());
            if (exist.isPresent()) order.setUser(exist.get());
            else throw new ValidationException("Neexistující uživatel");
        }
        return this.create(order);
    }

    @Transactional
    @Override
    public Order create(Order record) {
        Objects.requireNonNull(record);
        if (record.getStatus() == null) {
            record.setStatus(OrderStatus.NEW);
        }
        this.storage.reserveFlowers(record.getContains());
        return super.create(record);
    }

    public Map<Integer,Integer> checkItemsInStock(Map<Integer,Integer> cart) {
        return this.storage.itemsInStock(cart);
    }

    private void updateAddress (OrderAddress original, OrderAddress address) {
        if (address.getStreet() != null) {
            original.setStreet(address.getStreet());
        }
        if (address.getCity() != null) {
            original.setCity(address.getCity());
        }
        if (address.getZip() != null) {
            original.setZip(address.getZip());
        }
    }

    private boolean isAddressEmpty (Address address) {
        return address.getStreet() == null && address.getCity() == null && address.getZip() == null;
    }

    private void updateItemCounts(Order order, Map<Integer,Integer> keyCounts) {
        // remove
        if (order.getContains() != null) {
            Iterator<OrderItem> it = order.getContains().iterator();
            while (it.hasNext()) {
                if (!keyCounts.containsKey(it.next().getBouquet().getId())) it.remove();
            }
            order.calculateTotalPrice();
        }
        // add
        keyCounts.forEach((bouquetId, count) -> {
            Optional<Bouquet> bouquet = this.bouquetDao.findById(bouquetId);
            if (bouquet.isPresent()) order.addOrderItem(bouquet.get(), count);
            else throw new ValidationException("Neexistující kytice");
        });
    }
}
