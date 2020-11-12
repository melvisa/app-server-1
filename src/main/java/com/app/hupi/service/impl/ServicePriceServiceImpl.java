package com.app.hupi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.domain.ServicePrice;
import com.app.hupi.mapper.ServicePriceMapper;
import com.app.hupi.service.ServicePriceService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;


@Service
public class ServicePriceServiceImpl implements ServicePriceService {

	@Autowired
	private ServicePriceMapper servicePriceMapper;
	
	@Override
	public ServicePrice addServicePrice(ServicePrice servicePrice) {
		servicePriceMapper.insert(servicePrice);
		return servicePrice;
	}

	@Override
	public List<ServicePrice> queryServicePriceByType(String type) {
		EntityWrapper<ServicePrice> wrapper=new EntityWrapper<ServicePrice>();
		wrapper.eq("type", type).orderBy("weight desc");
		return servicePriceMapper.selectList(wrapper);
	}

	@Override
	public int deleteServicePrice(String id) {
		return servicePriceMapper.deleteById(id);
	}

}
