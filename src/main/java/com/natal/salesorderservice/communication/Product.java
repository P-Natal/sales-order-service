package com.natal.salesorderservice.communication;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Product {

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("price")
    private double price;

    @JsonProperty("available")
    private boolean available;

    @JsonProperty("registryDate")
    private Date registryDate;

    @JsonProperty("lastUpdate")
    private Date lastUpdate;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public Date getRegistryDate() {
        return registryDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }
}
