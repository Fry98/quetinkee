package com.quetinkee.eshop.model.projection;

import com.quetinkee.eshop.model.Address;
import com.quetinkee.eshop.model.Item;

import java.util.Set;

public interface OrderList extends InterfaceList{

    public String getUserFirstName();

    public String getUserLastName();

    public String getUserMail();

    public String getUserPhone();

    public Address getUserDeliveryAddress();

    public Address getUserBillingAddress();

    public Set<Item> getOrderItems();
}
