package com.natal.salesorderservice.communication;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "subscription", url = "${subscription.host}/subscription")
public interface SubscriptionClient {

    @GetMapping("/{document}/eligibility")
    ResponseEntity<ClientEligibility> getClientEligibility(@PathVariable("document") String document);

    @PutMapping("/{document}/eligibility")
    ResponseEntity<ClientEligibility> setClientEligibility(@PathVariable("document") String document, ClientEligibility clientEligibility);

}
