package com.natal.salesorderservice.controller;

import com.natal.salesorderservice.controller.to.CreateOrderTO;
import com.natal.salesorderservice.controller.to.OrderTO;
import com.natal.salesorderservice.controller.to.UpdateOrderTO;
import com.natal.salesorderservice.service.SalesOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sales-order")
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;

    @PostMapping
    public ResponseEntity<OrderTO> create(@RequestBody CreateOrderTO createOrderTO){
        try{
            OrderTO order = salesOrderService.create(createOrderTO);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping
    public ResponseEntity<OrderTO> update(@RequestBody UpdateOrderTO updateOrderTO){
        try{
            OrderTO order = salesOrderService.update(updateOrderTO);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<OrderTO> getByExternalId(@PathVariable String externalId){
        try{
            OrderTO order = salesOrderService.getOrder(externalId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public List<OrderTO> getAllOrders(){
        return salesOrderService.getAll();
    }

}
