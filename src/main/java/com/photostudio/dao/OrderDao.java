package com.photostudio.dao;

import com.photostudio.entity.order.FilterParameters;
import com.photostudio.entity.order.Order;

import java.util.List;

public interface OrderDao {
    List<Order> getAll();

    List<Order> getOrdersByParameters(FilterParameters filterParameters);

    Order getOrderByIdInStatusNew(int id);

    void delete(long id);
}
