package com.app.hupi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.hupi.domain.ServicePrice;
import com.app.hupi.mapper.ServicePriceMapper;
import com.app.hupi.service.ServicePriceService;
import com.app.hupi.util.StringUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


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

	@Override
	public PageInfo<ServicePrice> pageInfo(int pageNum, int pageSize, String type) {
		PageHelper.startPage(pageNum, pageSize);
		EntityWrapper<ServicePrice> wrapper=new EntityWrapper<ServicePrice>();
		if(StringUtil.isNotEmpty(type)) {
			wrapper.eq("type", type);
		}
		wrapper.orderBy("weight desc");
		
		List<ServicePrice> list=servicePriceMapper.selectList(wrapper);
		
		PageInfo<ServicePrice> pageInfo=new PageInfo<ServicePrice>(list);
		return pageInfo;
	}

	@Override
	public ServicePrice udpateServicePrice(ServicePrice servicePrice) {
		servicePriceMapper.updateById(servicePrice);
		return servicePrice;
	}

}
