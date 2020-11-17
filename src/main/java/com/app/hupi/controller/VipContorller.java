package com.app.hupi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.hupi.constant.DataResult;
import com.app.hupi.domain.Employer;
import com.app.hupi.domain.Tutoring;
import com.app.hupi.domain.VipOrder;
import com.app.hupi.service.EmployerService;
import com.app.hupi.service.TutoringService;
import com.app.hupi.service.VipOrderService;
import com.app.hupi.service.VipService;
import com.app.hupi.vo.VipVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/vip")
@Api(tags = {"Vip模块"})
@RestController
public class VipContorller {

	@Autowired
	private VipService vipService;
	@Autowired
	private TutoringService tutoringService;
	
	@Autowired
	private EmployerService employerService;
	@Autowired
	private VipOrderService vipOrderService;
	
	@ApiOperation(value = "查询雇主vip界面信息")
	@GetMapping("/vipInfoWithEmployer")
	public DataResult<List<VipVO>> vipInfoWithEmployer() {
		List<VipVO> list=vipService.listVip("E");
		return DataResult.getSuccessDataResult(list);
	}
	
	@ApiOperation(value = "查询vip界面信息")
	@GetMapping("/vipInfoWithTutoring")
	public DataResult<List<VipVO>> vipInfoWithTutoring() {
		List<VipVO> list=vipService.listVip("T");
		return DataResult.getSuccessDataResult(list);
	}
	
	
	@ApiOperation(value = "雇主生成vip订单")
	@PostMapping("/vipEmployerOrder")
	public DataResult<String> vipEmployerOrder(@RequestHeader("token")String token,String vipId) {
		Employer employer=employerService.queryEmployerByToken(token);
		VipOrder  vipOrder=vipOrderService.addVipOrder(employer.getId(), "E", vipId);
		return DataResult.getSuccessDataResult(vipOrder.getOrderId());
	}
	
	@ApiOperation(value = "家教生成vip订单")
	@PostMapping("/vipTutoringOrder")
	public DataResult<String> vipTutoringOrder(@RequestHeader("token")String token,String vipId) {
		Tutoring tutoring=tutoringService.queryTutoringByToken(token);
		VipOrder  vipOrder=vipOrderService.addVipOrder(tutoring.getId(), "T", vipId);
		return DataResult.getSuccessDataResult(vipOrder.getOrderId());
	}
	
}
