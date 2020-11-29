package com.app.hupi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.ServicePrice;
import com.app.hupi.service.ServicePriceService;
import com.app.hupi.util.BeanUtil;
import com.app.hupi.util.KiteUUID;
import com.app.hupi.vo.ServicePriceAddVo;
import com.github.pagehelper.PageInfo;

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
	
	
	@ApiOperation(value = "分页查询推荐服务价格")
	@GetMapping("/pageServicePrice")
	public DataResult<PageInfo<ServicePrice>> pageServicePrice(int pageNum,int pageSize,String type) {
		PageInfo<ServicePrice> pageInfo=servicePriceService.pageInfo(pageNum, pageSize, type);
		return DataResult.getSuccessDataResult(pageInfo);
		
	}
	
	@ApiOperation(value = "新增推荐服务价格")
	@PostMapping("/addServicePrice")
	public DataResult<ServicePrice> addServicePrice(@RequestBody ServicePriceAddVo servicePriceAddVo) {
		ServicePrice  servicePrice=new ServicePrice();
		BeanUtil.copyProperties(servicePriceAddVo, servicePrice);
		servicePrice.setId(KiteUUID.getId());
		servicePriceService.addServicePrice(servicePrice);
		return DataResult.getSuccessDataResult(servicePrice);
		
	}
	
	@ApiOperation(value = "删除 返回1 删除成功")
	@GetMapping("/deleteServicePrice")
	public DataResult<String> deleteServicePrice(String id) {
		int i=servicePriceService.deleteServicePrice(id);
		return DataResult.getSuccessDataResult(i+"");
		
	}
	
	
	
}
