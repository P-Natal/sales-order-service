package com.natal.salesorderservice.service;

import com.natal.salesorderservice.controller.to.CreateOrderTO;
import com.natal.salesorderservice.controller.to.OrderTO;
import com.natal.salesorderservice.controller.to.UpdateOrderTO;
import com.natal.salesorderservice.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalesOrderService {
    OrderTO create(CreateOrderTO createOrderTO);
    OrderTO getOrder(String externalId) throws NotFoundException;
    List<OrderTO> getAll();
    OrderTO update(String externalId, UpdateOrderTO updateOrderTO);

    void cancelOrdersByDocument(String document);
}
