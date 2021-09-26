package com.natal.salesorderservice.communication;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog", url = "${catalog.host}/product")
public interface CatalogClient {

    @GetMapping("/{productCode}")
    Product getProduct(@PathVariable("productCode") String productCode);

}
