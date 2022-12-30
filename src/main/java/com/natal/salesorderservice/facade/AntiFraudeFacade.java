package com.natal.salesorderservice.facade;

import com.natal.salesorderservice.infrastructure.entity.OrderEntity;
import com.natal.salesorderservice.infrastructure.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AntiFraudeFacade {

    @Autowired
    private OrderRepository orderRepository;

    public boolean excedeuLimiteDeOrdensPendentes(String clientDocument){
        log.info("Buscando ordens pendentes para cliente com documento: {}", clientDocument);
        List<OrderEntity> ordensPendentes = orderRepository.findByClientDocumentAndStatus(clientDocument, "CREATED");
        if (ordensPendentes != null){
            log.info("Cliente com documento {} possui {} ordens pendentes", clientDocument, ordensPendentes.size());
            if (ordensPendentes.size() > 2){
                return true;
            }
        }
        else {
            log.info("Nenhuma ordem encontrada para cliente com documento {}", clientDocument);
        }
        return false;
    }

}
