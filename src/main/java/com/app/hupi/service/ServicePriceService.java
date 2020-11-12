package com.app.hupi.service;

import java.util.List;

import com.app.hupi.domain.ServicePrice;

public interface ServicePriceService {

	ServicePrice addServicePrice(ServicePrice servicePrice);
	
	List<ServicePrice> queryServicePriceByType(String type);
	
	int deleteServicePrice(String id);
}
