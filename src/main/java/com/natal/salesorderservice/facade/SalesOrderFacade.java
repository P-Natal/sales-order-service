package com.natal.salesorderservice.facade;

import com.natal.salesorderservice.amqp.SalesOrderEvent;
import com.natal.salesorderservice.communication.CatalogClient;
import com.natal.salesorderservice.communication.ClientEligibility;
import com.natal.salesorderservice.communication.Product;
import com.natal.salesorderservice.communication.SubscriptionClient;
import com.natal.salesorderservice.controller.to.CreateOrderTO;
import com.natal.salesorderservice.controller.to.OrderTO;
import com.natal.salesorderservice.controller.to.UpdateOrderTO;
import com.natal.salesorderservice.exception.NotFoundException;
import com.natal.salesorderservice.exception.AntiFraudeException;
import com.natal.salesorderservice.exception.EligibilityException;
import com.natal.salesorderservice.infrastructure.entity.OrderEntity;
import com.natal.salesorderservice.infrastructure.repository.OrderRepository;
import com.natal.salesorderservice.service.SalesOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class SalesOrderFacade implements SalesOrderService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private CatalogClient catalogClient;
    @Autowired
    private SubscriptionClient subscriptionClient;
    @Autowired
    private AntiFraudeFacade antiFraudeFacade;

//    @Autowired
//    private OrderPublisher orderPublisher;

    @Override
    public OrderTO create(CreateOrderTO createOrderTO) {
        try {
            log.info("Criando ordem de venda: {}", createOrderTO.toString());
            String externalId = gerarExternalId();
            String clientDocument = createOrderTO.getClientDocument();
            String productCode = createOrderTO.getProductCode();

            ResponseEntity<Product> productResponse = catalogClient.getProduct(productCode);
            ResponseEntity<ClientEligibility> clientEligibilityResponse = subscriptionClient.getClientEligibility(clientDocument);
            if (!productResponse.hasBody()){
                log.info("Código de produto recebido não encontrado: {}", productCode);
                throw new RuntimeException("Código de produto recebido não encontrado: {}"+ productCode);
            }
            else if (!clientEligibilityResponse.hasBody()){
                throw new RuntimeException("Falha na consulta de elegibilidade do cliente com documento: "+ clientDocument);
            }
            else if (!clientEligibilityResponse.getBody().isEligible()){
                log.error("Cliente com documento {} nao esta elegivel", clientDocument);
                throw new EligibilityException(clientEligibilityResponse.getBody().getReason());
            }
            else if (antiFraudeFacade.excedeuLimiteDeOrdensPendentes(clientDocument)){
                subscriptionClient.setClientEligibility(
                        clientDocument,
                        new ClientEligibility(false, "Limite de ordens pendentes")
                );
                throw new AntiFraudeException("Limite de ordens pendentes");
            }

            OrderEntity orderEntity = new OrderEntity(
                    externalId,
                    clientDocument,
                    productCode,
                    "CREATED",
                    productResponse.getBody().getPrice()
            );
            log.info("Persistindo ordem de venda: {}", orderEntity.toString());
            OrderEntity persistedOrder = repository.save(orderEntity);
            publishOrder(persistedOrder);
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
            log.error("Erro ao criar ordem de venda {} - {}", createOrderTO, e.getMessage());
            throw e;
        }
    }

    private String gerarExternalId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public OrderTO getOrder(String externalId) throws NotFoundException {
        OrderTO order = findOrderByExternalId(externalId);
        if (order==null){
            throw new NotFoundException("Order with externalId "+externalId+" Not Found");
        }
        return order;
    }

    @Override
    public List<OrderTO> getAll() {
        log.info("Buscando ordens de venda persistidas no banco");
        return findOrders();
    }

    @Override
    public OrderTO update(UpdateOrderTO updateOrderTO) {
        OrderEntity existingOrderEntity = repository.findByExternalId(updateOrderTO.getExternalId());
        if (existingOrderEntity == null){
            log.info("Order com externalId {} não existe", updateOrderTO.getExternalId());
            return null;
        }
        log.info("Atualizando ordem de externalId {} para status {}", updateOrderTO.getExternalId(), updateOrderTO.getStatus());
        existingOrderEntity.setStatus(updateOrderTO.getStatus());
        OrderEntity updatedOrderEntity = repository.save(existingOrderEntity);
        return new OrderTO(
                updatedOrderEntity.getExternalId(),
                updatedOrderEntity.getClientDocument(),
                updatedOrderEntity.getProductCode(),
                updatedOrderEntity.getStatus(),
                updatedOrderEntity.getRegistryDate(),
                updatedOrderEntity.getLastUpdate()
        );
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

    private void publishOrder(OrderEntity persistedOrder) {
        SalesOrderEvent event = new SalesOrderEvent(
                persistedOrder.getExternalId(),
                persistedOrder.getClientDocument(),
                persistedOrder.getProductCode(),
                persistedOrder.getStatus(),
                persistedOrder.getRegistryDate(),
                persistedOrder.getLastUpdate()
        );
//        orderPublisher.sendMessage(new Gson().toJson(event));
    }
}
