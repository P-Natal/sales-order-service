package com.natal.salesorderservice.controller;

import com.natal.salesorderservice.controller.to.CreateOrderTO;
import com.natal.salesorderservice.controller.to.OrderTO;
import com.natal.salesorderservice.service.SalesOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sales-order")
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;

    @PostMapping
    public OrderTO create(@RequestBody CreateOrderTO createOrderTO){
        return salesOrderService.create(createOrderTO);
    }

    @GetMapping("/{externalId}")
    public OrderTO getByExternalId(@PathVariable String externalId){
        return salesOrderService.getOrder(externalId);
    }

    @GetMapping
    public List<OrderTO> getAllOrders(){
        return salesOrderService.getAll();
    }

}
