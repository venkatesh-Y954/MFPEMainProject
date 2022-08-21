package com.realestate.customer.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.realestate.customer.entities.Property;

@FeignClient(name = "property-service", url = "http://localhost:8082/property")
public interface PropertyServiceClient {

	@GetMapping("/getAllProperties")
	List<Property> getAllProperties(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader)
			throws Exception;
	
}
