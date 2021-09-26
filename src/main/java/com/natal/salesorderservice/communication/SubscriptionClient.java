package com.natal.salesorderservice.communication;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "subscription", url = "${subscription.host}/subscription")
public interface SubscriptionClient {

    @GetMapping("/{document}/eligibility")
    ClientEligibility getClientEligibility(@PathVariable("document") String document);

    @PostMapping("/{document}/eligibility")
    ClientEligibility setClientEligibility(@PathVariable("document") String document, ClientEligibility clientEligibility);

}
