package com.natal.salesorderservice.service;

import com.natal.salesorderservice.controller.OrderTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalesOrderService {
    void create(OrderTO orderTO);
    OrderTO getOrder(String externalId);
    List<OrderTO> getAll();
}
