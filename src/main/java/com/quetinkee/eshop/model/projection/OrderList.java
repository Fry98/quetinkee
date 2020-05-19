package com.quetinkee.eshop.model.projection;

import com.quetinkee.eshop.model.enums.OrderStatus;
import com.quetinkee.eshop.model.enums.PaymentOption;
import java.sql.Date;
import java.sql.Time;

public interface OrderList extends InterfaceList{

    public String getUserFirstName();

    public String getUserLastName();

    public String getUserMail();

    public String getUserPhone();

    public OrderStatus getStatus();

    public PaymentOption getPayment();

    public Date getDay();

    public Time getTime();
}
