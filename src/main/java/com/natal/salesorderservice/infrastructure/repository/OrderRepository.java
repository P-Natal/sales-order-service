package com.natal.salesorderservice.infrastructure.repository;

import com.natal.salesorderservice.infrastructure.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByClientDocument(String document);
    OrderEntity findByExternalId(String externalId);

    @Query("SELECT o FROM OrderEntity o WHERE o.clientDocument = ?1 AND o.status = ?2")
    List<OrderEntity> findByClientDocumentAndStatus(String clientDocument, String created);
}
