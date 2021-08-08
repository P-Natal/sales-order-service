package com.natal.salesorderservice.infrastructure.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Table(name = "sales_order")
public class OrderEntity extends EntityClass {

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(name = "client_document", nullable = false)
    private String clientDocument;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "status", nullable = false)
    private String status;

    public OrderEntity() {
    }

    public OrderEntity(String externalId, String clientDocument, String productCode, String status) {
        this.externalId = externalId;
        this.clientDocument = clientDocument;
        this.productCode = productCode;
        this.status = status;
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
