package com.natal.salesorderservice.facade;

import com.natal.salesorderservice.controller.to.CreateOrderTO;
import com.natal.salesorderservice.controller.to.OrderTO;
import com.natal.salesorderservice.infrastructure.entity.OrderEntity;
import com.natal.salesorderservice.infrastructure.repository.OrderRepository;
import com.natal.salesorderservice.service.SalesOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class SalesOrderFacade implements SalesOrderService {

    @Autowired
    private OrderRepository repository;

    @Override
    public OrderTO create(CreateOrderTO createOrderTO) {
        try {
            log.info("Criando ordem de venda: {}", createOrderTO.toString());
            String externalId = gerarExternalId();
            OrderEntity orderEntity = new OrderEntity(
                    externalId,
                    createOrderTO.getClientDocument(),
                    createOrderTO.getProductCode(),
                    "CREATED"
            );
            log.info("Persistindo ordem de venda: {}", orderEntity.toString());
            OrderEntity persistedOrder = repository.save(orderEntity);
            return new OrderTO(
                    persistedOrder.getExternalId(),
                    persistedOrder.getClientDocument(),
                    persistedOrder.getProductCode(),
                    persistedOrder.getStatus(),
                    persistedOrder.getRegistryDate(),
                    persistedOrder.getLastUpdate()
            );
        }
        catch (Exception e){
            log.error("Erro ao criar ordem de venda {} ", createOrderTO, e);
        }
        return null;
    }

    private String gerarExternalId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public OrderTO getOrder(String externalId) {
        return findOrderByExternalId(externalId);
    }

    @Override
    public List<OrderTO> getAll() {
        log.info("Buscando ordens de venda persistidas no banco");
        return findOrders();
    }

    private List<OrderTO> findOrders() {
        List<OrderTO> orders = new ArrayList<>();
        try{
            List<OrderEntity> persistedOrderEntities = repository.findAll();
            for(OrderEntity orderEntity: persistedOrderEntities){
                OrderTO orderTO = new OrderTO();
                orderTO.setExternalId(orderEntity.getExternalId());
                orderTO.setClientDocument(orderEntity.getClientDocument());
                orderTO.setProductCode(orderEntity.getProductCode());
                orderTO.setStatus(orderEntity.getStatus());
                orderTO.setRegistryDate(orderEntity.getRegistryDate());
                orderTO.setLastUpdate(orderEntity.getLastUpdate());
                orders.add(orderTO);
            }
        }catch (Exception e){
            log.error("Falha ao buscar ordens de venda persistidas", e);
            orders.clear();
        }
        return orders;
    }

    private OrderTO findOrderByExternalId(String externalId) {
        OrderTO orderTO = new OrderTO();
        try{
            OrderEntity orderEntityPersisted = repository.findByExternalId(externalId);
            if (orderEntityPersisted !=null){
                orderTO.setExternalId(orderEntityPersisted.getExternalId());
                orderTO.setClientDocument(orderEntityPersisted.getClientDocument());
                orderTO.setProductCode(orderEntityPersisted.getProductCode());
                orderTO.setStatus(orderEntityPersisted.getStatus());
                orderTO.setRegistryDate(orderEntityPersisted.getRegistryDate());
                orderTO.setLastUpdate(orderEntityPersisted.getLastUpdate());
            }
            else {
                log.warn("Registro de ordem de venda com externalId {} não encontrado!", externalId);
                orderTO = null;
            }
        }catch (Exception e){
            log.error("Falha ao buscarordem de venda com externalId {} ", externalId, e);
            orderTO = null;
        }
        return orderTO;
    }
}
