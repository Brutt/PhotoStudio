package com.photostudio.service.impl;

import com.photostudio.ServiceLocator;
import com.photostudio.dao.OrderStatusDao;
import com.photostudio.entity.order.OrderStatus;
import com.photostudio.service.OrderStatusService;

public class DefaultOrderStatusService implements OrderStatusService {
    private OrderStatusDao orderStatusDao;

    public DefaultOrderStatusService(OrderStatusDao orderStatusDao) {
        this.orderStatusDao = orderStatusDao;
    }

    @Override
    public int getOrderStatusIdByStatusName(OrderStatus status) {
        return orderStatusDao.getOrderStatusIdByStatusName(status);
    }

}
