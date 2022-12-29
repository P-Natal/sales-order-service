package com.natal.salesorderservice.controller;

import com.natal.salesorderservice.controller.to.CreateOrderTO;
import com.natal.salesorderservice.controller.to.OrderTO;
import com.natal.salesorderservice.controller.to.UpdateOrderTO;
import com.natal.salesorderservice.exception.AntiFraudeException;
import com.natal.salesorderservice.exception.EligibilityException;
import com.natal.salesorderservice.exception.InvalidStatusException;
import com.natal.salesorderservice.exception.NotFoundException;
import com.natal.salesorderservice.service.SalesOrderService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/sales-order")
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateOrderTO createOrderTO){
        try{
            OrderTO order = salesOrderService.create(createOrderTO);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        }
        catch (EligibilityException | AntiFraudeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN);
        } catch (FeignException e){
            if (e.getMessage().contains("404")){
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{externalId}")
    public ResponseEntity<?> update(@PathVariable String externalId, @RequestBody UpdateOrderTO updateOrderTO){
        try{
            OrderTO order = salesOrderService.update(externalId, updateOrderTO);
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (InvalidStatusException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{externalId}")
    public ResponseEntity<OrderTO> getByExternalId(@PathVariable String externalId){
        try{
            UUID.fromString(externalId);
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        try{
            OrderTO order = salesOrderService.getOrder(externalId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        catch (NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(){
        List<OrderTO> orders = salesOrderService.getAll();
        if (!orders.isEmpty()){
            return ResponseEntity.ok(orders);
        }
        return ResponseEntity.noContent().build();
    }

}
