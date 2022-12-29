package com.natal.salesorderservice.controller;

import com.natal.salesorderservice.controller.to.CreateOrderTO;
import com.natal.salesorderservice.controller.to.OrderTO;
import com.natal.salesorderservice.controller.to.UpdateOrderTO;
import com.natal.salesorderservice.exception.AntiFraudeException;
import com.natal.salesorderservice.exception.EligibilityException;
import com.natal.salesorderservice.service.SalesOrderService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
