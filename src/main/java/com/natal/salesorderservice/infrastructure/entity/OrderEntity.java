package com.natal.salesorderservice.infrastructure.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "order")
public class OrderEntity extends EntityClass {

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(name = "client_document", nullable = false)
    private String clientDocument;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    public OrderEntity() {
    }

    public OrderEntity(String externalId, String clientDocument, String status, String productCode) {
        this.externalId = externalId;
        this.clientDocument = clientDocument;
        this.status = status;
        this.productCode = productCode;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id='" + this.getId() + '\'' +
                ", external_id='" + externalId + '\'' +
                ", client_document='" + clientDocument + '\'' +
                ", product_code='" + productCode + '\'' +
                ", status='" + status + '\'' +
                ", registry_date='" + this.getRegistryDate() + '\'' +
                ", last_update='" + this.getLastUpdate() + '\'' +
                '}';
    }
}
