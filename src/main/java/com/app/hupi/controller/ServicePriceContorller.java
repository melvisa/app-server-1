package com.app.hupi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.ServicePrice;
import com.app.hupi.service.ServicePriceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/servicePrice")
@Api(tags = {"服务价格"})
@RestController
public class ServicePriceContorller {

	@Autowired
	private ServicePriceService servicePriceService;
	
	@ApiOperation(value = "查询推荐服务价格")
	@GetMapping("/listservicePrice")
	public DataResult<List<ServicePrice>> listservicePrice(String type) {
		return DataResult.getSuccessDataResult(servicePriceService.queryServicePriceByType(type));
	}
	
	
	
	
	
}
