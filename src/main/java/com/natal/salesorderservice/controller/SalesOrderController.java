package com.natal.salesorderservice.controller;

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
    public void create(@RequestBody OrderTO orderTO){
        salesOrderService.create(orderTO);
    }

    @GetMapping("/{externalId}")
    public OrderTO getByExternalId(@PathVariable String externalId){
        return salesOrderService.getOrder(externalId);
    }

    @GetMapping
    public List<OrderTO> create(){
        return salesOrderService.getAll();
    }

}
