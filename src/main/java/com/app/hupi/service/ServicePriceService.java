package com.app.hupi.service;

import java.util.List;

import com.app.hupi.domain.ServicePrice;
import com.github.pagehelper.PageInfo;

public interface ServicePriceService {

	
	PageInfo<ServicePrice> pageInfo(int pageNum,int pageSize,String type);
	
	ServicePrice udpateServicePrice(ServicePrice servicePrice);
	
	ServicePrice addServicePrice(ServicePrice servicePrice);
	
	List<ServicePrice> queryServicePriceByType(String type);
	
	int deleteServicePrice(String id);
}
