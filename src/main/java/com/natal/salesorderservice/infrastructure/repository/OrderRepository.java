package com.natal.salesorderservice.infrastructure.repository;

import com.natal.salesorderservice.infrastructure.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByClientDocument(String document);
    OrderEntity findByExternalId(String externalId);
}
