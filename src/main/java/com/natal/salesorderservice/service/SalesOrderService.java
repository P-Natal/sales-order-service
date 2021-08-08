package com.natal.salesorderservice.service;

import com.natal.salesorderservice.controller.to.CreateOrderTO;
import com.natal.salesorderservice.controller.to.OrderTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalesOrderService {
    OrderTO create(CreateOrderTO createOrderTO);
    OrderTO getOrder(String externalId);
    List<OrderTO> getAll();
}
